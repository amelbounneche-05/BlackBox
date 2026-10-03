package com.pigeon.boitenoire.repository;

import com.pigeon.boitenoire.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository interface for executing data queries and CRUD operations on Event documents.
 */
@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}