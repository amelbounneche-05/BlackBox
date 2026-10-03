package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller dedicated to business intelligence and analytics endpoints.
 */
@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Endpoints for log analysis and event statistics")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;

    /**
     * Constructs the AnalyticsController with the required analytics service.
     * 
     * @param analyticsService the service handling analytical business logic
     */
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Calculates and retrieves user conversion funnel metrics.
     * 
     * @return a ResponseEntity containing the conversion funnel Document result
     */
    @GetMapping("/funnel")
    @Operation(summary = "Conversion Funnel Analysis", description = "Calculates the exact number of users who successfully progressed through connection, API call, and payment steps.")
    public ResponseEntity<Document> getFunnelAnalysis() {
        // Fetch conversion funnel data from the analytics service layer
        Document result = analyticsService.getConversionFunnel();
        return ResponseEntity.ok(result);
    }
}