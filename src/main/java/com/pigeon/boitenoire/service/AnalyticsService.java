package com.pigeon.boitenoire.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.bson.Document;

@Service
public class AnalyticsService {
    private final MongoTemplate mongoTemplate;

    public AnalyticsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Document getConversionFunnel() {
        AggregationOperation matchStep = Aggregation.match(
            Criteria.where("eventType").in("connection", "api_call", "subscription_payment")
        );
        AggregationOperation groupStep = Aggregation.group("userId")
            .addToSet("eventType").as("performedEvents");
        AggregationOperation matchFunnel = Aggregation.match(
            Criteria.where("performedEvents").all("connection", "api_call", "subscription_payment")
        );
        AggregationOperation countStep = Aggregation.count().as("totalConvertedUsers");

        Aggregation aggregation = Aggregation.newAggregation(matchStep, groupStep, matchFunnel, countStep);

        AggregationResults<Document> results = mongoTemplate.aggregate(
            aggregation, "events", Document.class
        );

        return results.getUniqueMappedResult() != null ? results.getUniqueMappedResult() : new Document("totalConvertedUsers", 0);
    }
}