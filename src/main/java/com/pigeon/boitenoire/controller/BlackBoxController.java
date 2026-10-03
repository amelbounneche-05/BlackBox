package com.pigeon.boitenoire.controller;

import com.pigeon.boitenoire.model.BlackBox;
import com.pigeon.boitenoire.repository.BlackBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing BlackBox log messages and persistent records.
 */
@RestController
@RequestMapping("/api/boitenoire")
public class BlackBoxController {

    @Autowired
    private BlackBoxRepository repository;

    /**
     * Retrieves all recorded black box messages from the database.
     * 
     * @return a list containing all BlackBox records
     */
    @GetMapping
    public List<BlackBox> getAllMessages() {
        return repository.findAll();
    }

    /**
     * Creates and stores a new black box message record.
     * 
     * @param message the content of the message to record
     * @return the newly saved BlackBox entity
     */
    @PostMapping
    public BlackBox createMessage(@RequestBody String message) {
        // Build a new black box record stamped with the current timestamp
        BlackBox box = new BlackBox(message, LocalDateTime.now().toString());
        return repository.save(box);
    }

    /**
     * Deletes a specific black box record using its unique identifier.
     * 
     * @param id the unique identifier of the record to delete
     */
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable String id) {
        repository.deleteById(id);
    }

    /**
     * Updates the message content of an existing black box record.
     * 
     * @param id the unique identifier of the record to update
     * @param newMessage the new message text to apply
     * @return a ResponseEntity containing the updated record, or 404 Not Found if missing
     */
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