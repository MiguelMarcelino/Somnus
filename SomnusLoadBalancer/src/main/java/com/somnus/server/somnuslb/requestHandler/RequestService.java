package com.somnus.server.somnuslb.requestHandler;

import com.somnus.server.somnuslb.exception.SomnusException;
import com.somnus.server.somnuslb.mirrorStatus.MirrorStatusService;
import com.somnus.server.somnuslb.mirrorStatus.domain.MirrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Service
public class RequestService {

    @Autowired
    private MirrorStatusService mirrorStatusService;

    private static Logger logger = Logger.getLogger("RequestService");
    private WebClient webClient;

    public RequestService() {
        this.webClient = WebClient.create();
    }

    /**
     * This method dispatches an incoming request to a mirror
     */
    public void dispatchToMirror() {
        // TODO: Load Balancer should equalize amount of requests sent to each instance,
        //  so that every instance has the same load
        AtomicBoolean success = new AtomicBoolean(false);  // review this
        List<MirrorInfo> mirrors = mirrorStatusService.getMirrors();
        int mirrorIndex = 0;
        AtomicReference<String> responseBody = null; // review this

        while (!success.get()) {
            // Attempt to send the request to the best mirror.
            // Since the list is ordered, the best Mirror is the first one
            if (mirrorIndex >= mirrors.size()) {
                break;
            }
            MirrorInfo dispatchMirror = mirrors.get(mirrorIndex);

            // Send request
            webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path(dispatchMirror.getMirror().getIpAddress() + "/get-mirror-status")
                                    .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().toEntity(String.class)
                    .timeout(Duration.ofMillis(60000))
                    .doOnError(err -> {
                        dispatchMirror.getMirror().incFailedRequests(1);
                        logger.info("Error when performing health check to instance with IP: " +
                                dispatchMirror.getMirror().getIpAddress() + "\n. Error Message: " + err.getMessage());
                    })
                    .doOnSuccess(response -> {
                        success.set(true);
                        responseBody.set(response.getBody());
//                        dispatchMirror.getMirror().addResponseTime(); // TODO
                        dispatchMirror.getMirror().incNumSentRequests(1);
                    })
                    .block(); // blocks waiting for response. May need to change in the future
        }

        if (!success.get()) {
            throw new SomnusException("No available Mirrors. Could not redirect request");
        }

        // TODO: Send response back to client
    }
}
