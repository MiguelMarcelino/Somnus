package com.somnus.server.backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.somnus.server.backend.systemmonitor.systemusage.SystemUsage;
import com.somnus.server.backend.teammembers.ContributionService;
import com.somnus.server.backend.teammembers.repository.ContributionRepository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.FileInputStream;
import java.io.IOException;

@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class SomnusBackendApplication extends SpringBootServletInitializer implements InitializingBean {

	@Autowired
	private ContributionService contributionService;

	public static void main(String[] args) {
		SpringApplication.run(SomnusBackendApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		contributionService.updateContributionRepo();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SomnusBackendApplication.class);
	}

}
