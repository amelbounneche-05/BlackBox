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

/**
 * REST controller for advanced event analytics and statistical aggregations.
 */
@RestController
@RequestMapping("/api/analytics")
public class EventController {

    private final EventAggregationService aggregationService;

    /**
     * Constructs the EventController with the required aggregation service.
     * 
     * @param aggregationService the service handling statistical queries
     */
    public EventController(EventAggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    /**
     * Retrieves the top active users based on event activity metrics within an optional date range.
     * 
     * @param startDate optional filter for the start timestamp
     * @param endDate optional filter for the end timestamp
     * @return a list of maps containing top active users data
     */
    @GetMapping("/top-users")
    public List<Map> getTopUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        // Delegate top active users aggregation to the service layer
        return aggregationService.getTopActiveUsers(startDate, endDate);
    }

    /**
     * Retrieves system error statistics within an optional date range.
     * 
     * @param startDate optional filter for the start timestamp
     * @param endDate optional filter for the end timestamp
     * @return a list of maps containing error distribution statistics
     */
    @GetMapping("/error-stats")
    public List<Map> getErrorStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        // Delegate error statistics aggregation to the service layer
        return aggregationService.getErrorStatistics(startDate, endDate);
    }

    /**
     * Retrieves endpoint performance metrics and response times within an optional date range.
     * 
     * @param startDate optional filter for the start timestamp
     * @param endDate optional filter for the end timestamp
     * @return a list of maps containing performance indicators
     */
    @GetMapping("/performance")
    public List<Map> getPerformanceStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        // Delegate endpoint performance analysis to the service layer
        return aggregationService.getEndpointPerformance(startDate, endDate);
    }
}