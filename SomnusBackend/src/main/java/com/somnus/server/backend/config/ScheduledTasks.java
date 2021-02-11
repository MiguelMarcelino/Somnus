package com.somnus.server.backend.config;

import com.somnus.server.backend.teammembers.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ContributionService contributionService;

    @Scheduled(cron = "0 0 2 * * *")
    public void updateContributions() {
        contributionService.updateContributionRepo();
    }
}
