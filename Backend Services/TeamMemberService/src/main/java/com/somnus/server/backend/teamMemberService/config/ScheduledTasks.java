package com.somnus.server.backend.teamMemberService.config;

import com.somnus.server.backend.teammembers.ContributionService;
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
public class ScheduledTasks {

    @Autowired
    private ContributionService contributionService;

    @Async
    @Scheduled(cron = "0 0 2 * * *")
    public void updateContributions() {
        contributionService.updateContributionRepo();
    }
}
