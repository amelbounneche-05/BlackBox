package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.service.DataGeneratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for administrative operations, 
 * such as triggering manual data seeding processes.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DataGeneratorService dataGeneratorService;

    /**
     * Constructs the AdminController with the required data generator service.
     * 
     * @param dataGeneratorService the service used to generate synthetic test data
     */
    public AdminController(DataGeneratorService dataGeneratorService) {
        this.dataGeneratorService = dataGeneratorService;
    }

    /**
     * Triggers the asynchronous generation of synthetic test data.
     * 
     * @return a status message confirming that background generation has started
     */
    @PostMapping("/generate")
    public String generateData() {
        // Execute data generation asynchronously in a background thread to prevent blocking HTTP threads
        new Thread(() -> dataGeneratorService.generateData()).start();
        return "Generation of 100,000+ events started in the background! Check the console.";
    }
}