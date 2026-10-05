package com.pigeon.boitenoire.repository;

import com.pigeon.boitenoire.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoDB repository interface for executing data queries and CRUD operations on Event documents.
 * Optimized with custom compound indexes to ensure high performance.
 */

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    /**
     * Retrieves events for a specific user, sorted by recency.
     * Backed by the compound index { userId: 1, timestamp: -1 } 
     * to eliminate expensive COLLSCAN operations.
     */

    @Query(value = "{ 'userId': ?0 }", sort = "{ 'timestamp': -1 }")
    List<Event> findByUserIdOrderByTimestampDesc(String userId);
}