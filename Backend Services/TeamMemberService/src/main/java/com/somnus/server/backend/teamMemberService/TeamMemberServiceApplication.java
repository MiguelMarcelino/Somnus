package com.somnus.server.backend.teamMemberService;

import com.somnus.server.backend.teamMemberService.teammembers.ContributionService;
import com.somnus.server.backend.teamMemberService.teammembers.TeamMemberService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
public class TeamMemberServiceApplication extends SpringBootServletInitializer implements InitializingBean {

	@Autowired
	private ContributionService contributionService;

	@Autowired
	private TeamMemberService teamMemberService;

	public static void main(String[] args) {
		SpringApplication.run(TeamMemberServiceApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		contributionService.updateContributionRepo();
		teamMemberService.updateTeamMemberOldestCommit();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TeamMemberServiceApplication.class);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
