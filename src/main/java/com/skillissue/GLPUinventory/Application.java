package com.skillissue.GLPUinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the GLPU Inventory application.
 * Enables Spring Boot auto-configuration and component scanning.
 */
@SpringBootApplication
public class Application {

	/**
	 * Starts the Spring Boot application.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
