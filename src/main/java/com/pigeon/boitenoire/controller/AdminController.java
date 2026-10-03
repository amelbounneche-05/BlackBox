package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.service.DataGeneratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DataGeneratorService dataGeneratorService;

    public AdminController(DataGeneratorService dataGeneratorService) {
        this.dataGeneratorService = dataGeneratorService;
    }

    @PostMapping("/generate")
    public String generateData() {
        new Thread(() -> dataGeneratorService.generateData()).start();
        return "Génération de 100 000 événements lancée en arrière-plan ! Vérifiez la console.";
    }
}