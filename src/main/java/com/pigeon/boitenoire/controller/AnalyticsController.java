package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Endpoints d'analyse des logs et événements")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/funnel")
    @Operation(summary = "Entonnoir de conversion", description = "Calcule le nombre d'utilisateurs ayant enchaîné connexion, appel API et paiement.")
    public ResponseEntity<Document> getFunnelAnalysis() {
        Document result = analyticsService.getConversionFunnel();
        return ResponseEntity.ok(result);
    }
}