package com.pigeon.boitenoire.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity representing a recorded BlackBox message item along with its creation timestamp.
 */
@Document(collection = "blackboxes")
public class BlackBox {
    
    @Id
    private String id;
    private String message;
    private String timestamp;

    /**
     * Default constructor for framework and instantiation usage.
     */
    public BlackBox() {}

    /**
     * Constructs a new BlackBox record with a specific message and timestamp.
     * 
     * @param message the log text or payload
     * @param timestamp the exact creation time string
     */
    public BlackBox(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}