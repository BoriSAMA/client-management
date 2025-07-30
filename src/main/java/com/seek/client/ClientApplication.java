package com.seek.client;

import com.seek.client.config.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Entry point for the Client Management application.
 * This class bootstraps the Spring Boot application context.
 */
@SpringBootApplication
public class ClientApplication {

	/**
	 * Main method used to launch the application.
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

}
