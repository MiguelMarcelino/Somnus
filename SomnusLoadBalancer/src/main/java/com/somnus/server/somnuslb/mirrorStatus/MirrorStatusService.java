package com.somnus.server.somnuslb.mirrorStatus;

import com.somnus.server.somnuslb.mirrorStatus.domain.MirrorInfo;
import com.somnus.server.somnuslb.mirrors.domain.Mirror;
import com.somnus.server.somnuslb.mirrors.dto.MirrorInfoDTO;
import com.somnus.server.somnuslb.observer.Observer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MirrorStatusService implements Observer {

    private List<MirrorInfo> mirrors;
    private WebClient webClient;
    private MirrorStatusComparator mirrorStatusComparator;
    private Logger logger = Logger.getLogger("MirrorStatusService");

    public MirrorStatusService() {
        mirrors = new ArrayList<>();
        mirrorStatusComparator = new MirrorStatusComparator();
        webClient = WebClient.create();
    }

    /**
     * Checks the health of all Mirrors and gathers information
     * about their current status
     */
    public void checkMirrorHealth() {
        for (MirrorInfo m : mirrors) {
            webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path(m.getMirror().getIpAddress() + "/get-mirror-status")
                                    .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(MirrorInfoDTO.class) // toEntity allows retrieval of status code
                    .timeout(Duration.ofMillis(60000))
                    .doOnError(err -> {
                        m.incNumFailedHealthChecks(1);
                        logger.info("Error when performing health check to instance with IP: " +
                                m.getMirror().getIpAddress() + "\n. Error Message: " + err.getMessage());
                    })
                    .doOnSuccess(mInfoDTO -> {
                        m.incNumConsecutiveSuccesses(1);
                        m.addCPUUsageMetric(mInfoDTO.getMeasuredCPUUsage());
                        m.addMemoryUsageMetric(mInfoDTO.getMeasuredMemoryUsage());
                        m.addSwapUsageMetric(mInfoDTO.getMeasuredSwapUsage());
                    })
                    .subscribeOn(Schedulers.parallel())
                    .subscribe();
            m.incNumTotalHealthChecks(1);
        }
    }

    public List<MirrorInfo> getMirrors() {
        return this.mirrors;
    }

    public void sortMirrorList() {
        Collections.sort(this.mirrors, (o1, o2) -> mirrorStatusComparator.compare(o1, o2));
    }

    @Override
    public void add(Object o) {
        if (o instanceof Mirror)
            mirrors.add(new MirrorInfo((Mirror) o));
    }

    @Override
    public void remove(Object o) {
        if (o instanceof Mirror)
            mirrors.removeIf(x -> x.getMirror().getId().equals(((Mirror) o).getId()));
    }

}
