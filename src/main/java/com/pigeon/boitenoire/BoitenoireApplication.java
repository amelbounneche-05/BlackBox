package com.pigeon.boitenoire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point for the Boîte Noire Spring Boot system.
 */
@SpringBootApplication
public class BoitenoireApplication {
    
    /**
     * Main execution method launching the Spring Boot application container.
     * 
     * @param args command line arguments passed during startup
     */
    public static void main(String[] args) {
        SpringApplication.run(BoitenoireApplication.class, args);
    }
}