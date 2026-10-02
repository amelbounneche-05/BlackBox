package com.pigeon.boitenoire.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class EventAggregationService {

    private final MongoTemplate mongoTemplate;

    public EventAggregationService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Map> getTopActiveUsers(LocalDateTime startDate, LocalDateTime endDate) {
        Criteria criteria = new Criteria();
        if (startDate != null && endDate != null) {
            criteria = Criteria.where("timestamp").gte(startDate).lte(endDate);
        }

        Aggregation aggregation = newAggregation(
            match(criteria),
            group("userId").count().as("eventCount"),
            sort(Sort.Direction.DESC, "eventCount"),
            limit(10),
            project("eventCount").and("_id").as("userId")
        );
        return mongoTemplate.aggregate(aggregation, "events", Map.class).getMappedResults();
    }

    public List<Map> getErrorStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        Criteria criteria = new Criteria();
        if (startDate != null && endDate != null) {
            criteria = Criteria.where("timestamp").gte(startDate).lte(endDate);
        }

        Aggregation aggregation = newAggregation(
            match(criteria),
            group("statusCode").count().as("errorCount"),
            sort(Sort.Direction.DESC, "errorCount"),
            project("errorCount").and("_id").as("statusCode")
        );
        return mongoTemplate.aggregate(aggregation, "events", Map.class).getMappedResults();
    }

    public List<Map> getEndpointPerformance(LocalDateTime startDate, LocalDateTime endDate) {
        Criteria criteria = new Criteria();
        if (startDate != null && endDate != null) {
            criteria = Criteria.where("timestamp").gte(startDate).lte(endDate);
        }

        Aggregation aggregation = newAggregation(
            match(criteria),
            group("endpoint")
                .count().as("totalCalls")
                .avg("responseTimeMs").as("avgResponseTime")
                .max("responseTimeMs").as("maxResponseTime"),
            sort(Sort.Direction.DESC, "avgResponseTime"),
            project("totalCalls", "avgResponseTime", "maxResponseTime").and("_id").as("endpoint")
        );
        return mongoTemplate.aggregate(aggregation, "events", Map.class).getMappedResults();
    }
}