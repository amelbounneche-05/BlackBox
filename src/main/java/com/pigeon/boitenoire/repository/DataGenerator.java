package com.pigeon.boitenoire.repository;

import com.pigeon.boitenoire.model.Event;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataGenerator implements CommandLineRunner {

    private final EventRepository eventRepository;

    public DataGenerator(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (eventRepository.count() > 0) {
            System.out.println("Des donnees existent deja. Generation ignoree.");
            return;
        }

        System.out.println("Generation de 100 000+ evenements en cours...");
        List<Event> batch = new ArrayList<>();
        Random random = new Random();
        String[] types = {"CONNECTION", "PAYMENT", "ERROR", "API_CALL", "NOTIFICATION"};
        String[] users = {"user_1", "user_2", "user_3", "user_vip_1", "user_vip_2"};
        String[] endpoints = {"/api/login", "/api/messages", "/api/pay", "/api/profile"};

        LocalDateTime startDate = LocalDateTime.now().minusYears(1);

        for (int i = 0; i < 100500; i++) {
            Event event = new Event();
            event.setEventType(types[random.nextInt(types.length)]);
            event.setUserId(users[random.nextInt(users.length)]);
            event.setTimestamp(startDate.plusMinutes(random.nextInt(525600)));

            switch (event.getEventType()) {
                case "ERROR":
                    event.setErrorCode("ERR_" + (500 + random.nextInt(5)));
                    event.setErrorMessage("Internal server error or timeout");
                    break;
                case "PAYMENT":
                    event.setAmount(10.0 + (99.0 * random.nextDouble()));
                    event.setCurrency("EUR");
                    break;
                case "API_CALL":
                    event.setEndpoint(endpoints[random.nextInt(endpoints.length)]);
                    event.setResponseTimeMs(50 + random.nextInt(950));
                    event.setStatusCode(random.nextDouble() > 0.1 ? 200 : 500);
                    break;
                case "CONNECTION":
                case "NOTIFICATION":
                    event.setIpAddress("192.168.1." + random.nextInt(255));
                    event.setNotificationChannel(random.nextBoolean() ? "EMAIL" : "PUSH");
                    break;
            }

            batch.add(event);

            if (batch.size() == 5000) {
                eventRepository.saveAll(batch);
                batch.clear();
                System.out.println((i + 1) + " evenements generes...");
            }
        }

        if (!batch.isEmpty()) {
            eventRepository.saveAll(batch);
        }

        System.out.println("Generation terminee avec succes !");
    }
}