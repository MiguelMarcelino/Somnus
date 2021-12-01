package com.somnus.server.postservice.notifications.config;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PusherConfig {

    @Value("${pusher.config.appid}")
    private String pusherAppId;

    @Value("${pusher.config.key}")
    private String pusherKey;

    @Value("${pusher.config.secret}")
    private String pusherSecret;

    @Value("${pusher.config.cluster}")
    private String pusherCluster;

    private Pusher pusher;

    public void createPusherConnection() {
        this.pusher = new Pusher(pusherAppId, pusherKey, pusherSecret);
        this.pusher.setCluster(pusherCluster);
        this.pusher.setEncrypted(true);

//        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));
    }

    public Pusher getPusher() {
        return pusher;
    }
}
