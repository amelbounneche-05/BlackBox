package com.pigeon.boitenoire.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blackboxes")
public class BlackBox {
    @Id
    private String id;
    private String message;
    private String timestamp;

    public BlackBox() {}

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