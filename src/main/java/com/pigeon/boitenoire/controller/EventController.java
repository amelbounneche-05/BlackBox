package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.service.EventAggregationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class EventController {

    private final EventAggregationService aggregationService;

    public EventController(EventAggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @GetMapping("/top-users")
    public List<Map> getTopUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return aggregationService.getTopActiveUsers(startDate, endDate);
    }

    @GetMapping("/error-stats")
    public List<Map> getErrorStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return aggregationService.getErrorStatistics(startDate, endDate);
    }

    @GetMapping("/performance")
    public List<Map> getPerformanceStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return aggregationService.getEndpointPerformance(startDate, endDate);
    }
}