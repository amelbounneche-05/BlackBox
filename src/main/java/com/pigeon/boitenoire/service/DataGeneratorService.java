package com.pigeon.boitenoire.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataGeneratorService implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    public DataGeneratorService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Service de génération de données prêt ---");
    }

    public void generateData() {
        mongoTemplate.dropCollection("events");

        List<Map<String, Object>> batch = new ArrayList<>();
        Random random = new Random();

        List<String> users = generateUserPopulation(500);
        
        String[] eventTypes = {"connection", "subscription_payment", "app_error", "api_call", "notification_sent"};
        String[] endpoints = {"/api/v1/messages", "/api/v1/users", "/api/v1/auth", "/api/v1/payments"};

        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        int totalEvents = 100000;

        System.out.println("Génération de " + totalEvents + " événements en cours...");

        for (int i = 0; i < totalEvents; i++) {
            Map<String, Object> event = new HashMap<>();
            
            LocalDateTime timestamp = randomTimestamp(startDate, random);
            event.put("timestamp", timestamp);

            String userId = users.get(getParetoIndex(users.size(), random));
            event.put("userId", userId);

            String eventType = eventTypes[random.nextInt(eventTypes.length)];
            event.put("eventType", eventType);

            event.put("endpoint", endpoints[random.nextInt(endpoints.length)]);
            
            int statusCode;
            double randVal = random.nextDouble();
            if (randVal > 0.85) {
                statusCode = 500; 
            } else if (randVal > 0.7) {
                statusCode = 400;
            } else {
                statusCode = 200;
            }
            event.put("statusCode", statusCode);
            
            int responseTime = 10 + (int)(Math.pow(random.nextDouble(), 2) * 1200);
            event.put("responseTimeMs", responseTime);

            if ("subscription_payment".equals(eventType)) {
                Double[] amounts = {9.99, 19.99, 49.99};
                event.put("amount", amounts[random.nextInt(amounts.length)]);
                event.put("currency", "EUR");
            } else if ("app_error".equals(eventType)) {
                event.put("stackTrace", "NullPointerException at com.pigeon.boitenoire...");
            }

            batch.add(event);

            if (batch.size() >= 5000) {
                mongoTemplate.insert(batch, "events");
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            mongoTemplate.insert(batch, "events");
        }

        System.out.println("Génération terminée avec succès ! 100 000 événements insérés.");
    }

    private List<String> generateUserPopulation(int size) {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            users.add("user_" + String.format("%03d", i));
        }
        return users;
    }

    private int getParetoIndex(int size, Random random) {
        int index = (int) (size * Math.pow(random.nextDouble(), 3));
        return Math.min(index, size - 1);
    }

    private LocalDateTime randomTimestamp(LocalDateTime start, Random random) {
        int daysToAdd = random.nextInt(365);
        int hour = getRealisticHour(random);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        return start.plusDays(daysToAdd).withHour(hour).withMinute(minute).withSecond(second);
    }

    private int getRealisticHour(Random random) {
        double d = random.nextDouble();
        if (d < 0.6) {
            return 9 + random.nextInt(10);
        } else if (d < 0.85) {
            return 19 + random.nextInt(5);
        } else {
            return random.nextInt(9);
        }
    }
}