package com.somnus.server.backend;

import com.somnus.server.backend.systemmonitor.systemusage.SystemUsage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

	public static void main(String[] args) {
		SpringApplication.run(SomnusBackendApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		// TODO
	}
}
