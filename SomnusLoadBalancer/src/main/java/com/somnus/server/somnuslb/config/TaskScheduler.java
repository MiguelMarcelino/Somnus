package com.somnus.server.somnuslb.config;

import com.somnus.server.somnuslb.mirrorStatus.MirrorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
@EnableAsync
public class TaskScheduler {

    @Autowired
    private MirrorStatusService mirrorStatusService;

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    public void performHealthCheck() {
        mirrorStatusService.checkMirrorHealth();
        mirrorStatusService.sortMirrorList();
    }
}
