package com.somnus.server.backend;

import com.somnus.server.backend.notifications.config.PusherConfig;
import com.somnus.server.backend.teammembers.ContributionService;
import com.somnus.server.backend.teammembers.TeamMemberService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class SomnusBackendApplication extends SpringBootServletInitializer implements InitializingBean {

	@Autowired
	private ContributionService contributionService;

	@Autowired
	private TeamMemberService teamMemberService;

	@Autowired
	private PusherConfig pusherConfig;

	public static void main(String[] args) {
		SpringApplication.run(SomnusBackendApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		contributionService.updateContributionRepo();
		teamMemberService.updateTeamMemberOldestCommit();
		pusherConfig.createPusherConnection();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SomnusBackendApplication.class);
	}

}
