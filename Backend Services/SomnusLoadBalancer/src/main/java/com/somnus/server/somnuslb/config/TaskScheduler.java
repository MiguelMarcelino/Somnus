package com.somnus.server.somnuslb.config;

import com.somnus.server.somnuslb.instances.InstanceDataService;
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
    private InstanceDataService instanceDataService;

    @Async
    @Scheduled(cron = "*/30 * * * * *")
    public void performHealthCheck() {
        instanceDataService.checkMirrorHealth();
    }

    @Async
    @Scheduled(cron = "*/60 * * * * *")
    public void updateInstanceInformation() {
        instanceDataService.updateInstances();
    }
}
