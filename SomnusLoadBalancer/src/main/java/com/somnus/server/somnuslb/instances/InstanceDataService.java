package com.somnus.server.somnuslb.instances;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import com.somnus.server.somnuslb.instances.domain.InstanceData;
import com.somnus.server.somnuslb.instances.dto.InstanceDataDTO;
import com.somnus.server.somnuslb.instances.dto.StoredDataInfoDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InstanceDataService {

    // @Autowired //TODO: review this
    private DiscoveryClient discoveryClient;

    private Map<String, InstanceData> storedInstanceData;
    private WebClient webClient;
    private Logger logger = Logger.getLogger("MirrorStatusService");

    public InstanceDataService() {
        storedInstanceData = new HashMap<>();
        webClient = WebClient.create();
    }

    /**
     * Updates the stored instance state Map
     */
    public void updateInstances() {
        List<Application> applications = discoveryClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            List<InstanceInfo> applicationsInstances = application.getInstances();
            for (InstanceInfo appInstance : applicationsInstances) {
                String id = appInstance.getId();
                if (!storedInstanceData.containsKey(id)) {
                    InstanceData iInfo = new InstanceData(appInstance.getIPAddr());
                    iInfo.setStatus(appInstance.getStatus());
                    storedInstanceData.put(id, iInfo);
                } else {
                    InstanceData iInfo = storedInstanceData.get(id);
                    iInfo.setStatus(appInstance.getStatus());
                }
            }
        }
    }

    /**
     * Checks the health of all Mirrors and gathers information
     * about their current status
     */
    public void checkMirrorHealth() {
        for (InstanceData iData : storedInstanceData.values()) {
            webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path(iData.getIpAddr() + "/get-mirror-status")
                                    .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(InstanceDataDTO.class) // toEntity allows retrieval of status code
                    .timeout(Duration.ofMillis(60000))
                    .doOnError(err -> {
                        iData.incNumFailedRequests(1);
                        logger.info("Error when performing health check to instance with IP: " +
                                iData.getIpAddr() + "\n. Error Message: " + err.getMessage());
                    })
                    .doOnSuccess(mInfoDTO -> {
                        iData.incNumSuccesses(1);
                        iData.addCPUUsageMetric(mInfoDTO.getMeasuredCPUUsage());
                        iData.addMemoryUsageMetric(mInfoDTO.getMeasuredMemoryUsage());
                        iData.addSwapUsageMetric(mInfoDTO.getMeasuredSwapUsage());
                    })
                    .subscribeOn(Schedulers.parallel())
                    .subscribe();
            iData.incNumTotalRequests(1);
        }
    }

    public List<StoredDataInfoDTO> getInstanceInfos() {
        return this.storedInstanceData.values().stream()
                .map(iData -> new StoredDataInfoDTO())
                .collect(Collectors.toList());
    }

}
