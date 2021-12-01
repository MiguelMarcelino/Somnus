package com.somnus.server.postservice.notifications.handlers;

import com.pusher.rest.Pusher;
import com.somnus.server.postservice.notifications.config.PusherConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationHandler {

    @Autowired
    private PusherConfig pusherConfig;

    public void sendNotification(String channelName, String eventName, Object content) {
        Pusher pusher = this.pusherConfig.getPusher();

        // trigger new event
        pusher.trigger(channelName, eventName, content);
    }
}
