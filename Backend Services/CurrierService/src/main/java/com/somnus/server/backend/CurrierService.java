package com.somnus.server.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class CurrierService {

	public static void main(String[] args) {
		SpringApplication.run(CurrierService.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
