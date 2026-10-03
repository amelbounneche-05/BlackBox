package com.pigeon.boitenoire.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Entity representing a tracked system event supporting multiple metric categories 
 * (such as errors, payment transactions, API performance calls, and connections).
 */
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String eventType;
    private String userId;
    private LocalDateTime timestamp;

    // Error-specific fields
    private String errorCode;
    private String errorMessage;

    // Payment-specific fields
    private Double amount;
    private String currency;

    // API call-specific fields
    private String endpoint;
    private Integer responseTimeMs;
    private Integer statusCode;

    // Connection and Notification-specific fields
    private String ipAddress;
    private String notificationChannel;

    /**
     * Default constructor for entity instantiation.
     */
    public Event() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public Integer getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(Integer responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getNotificationChannel() { return notificationChannel; }
    public void setNotificationChannel(String notificationChannel) { this.notificationChannel = notificationChannel; }
}