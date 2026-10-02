package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.model.BlackBox;
import com.pigeon.boitenoire.repository.BlackBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/boitenoire")
public class BlackBoxController {

    @Autowired
    private BlackBoxRepository repository;

    @GetMapping
    public List<BlackBox> getAllMessages() {
        return repository.findAll();
    }

    @PostMapping
    public BlackBox createMessage(@RequestBody String message) {
        BlackBox box = new BlackBox(message, LocalDateTime.now().toString());
        return repository.save(box);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable String id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlackBox> updateMessage(@PathVariable String id, @RequestBody String newMessage) {
        return repository.findById(id)
                .map(box -> {
                    box.setMessage(newMessage);
                    BlackBox updatedBox = repository.save(box);
                    return ResponseEntity.ok(updatedBox);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}