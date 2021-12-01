package com.somnus.server.postservice;

import com.somnus.server.postservice.notifications.config.PusherConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class PostServiceApplication extends SpringBootServletInitializer implements InitializingBean {

	@Autowired
	private PusherConfig pusherConfig;

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		pusherConfig.createPusherConnection();
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}