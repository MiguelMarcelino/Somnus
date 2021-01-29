package com.somnus.server.backend;

import com.somnus.server.backend.config.DatabaseConnection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SomnusBackendApplication extends SpringBootServletInitializer implements InitializingBean {

	@Autowired
	private DatabaseConnection databaseConnection;

	public static void main(String[] args) {
		SpringApplication.run(SomnusBackendApplication.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		// Runs on app startup
		databaseConnection.startMongoDBConnection();
	}
}
