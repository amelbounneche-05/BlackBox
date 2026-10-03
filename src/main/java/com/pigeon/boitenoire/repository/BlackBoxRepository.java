package com.pigeon.boitenoire.repository;

import com.pigeon.boitenoire.model.BlackBox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB repository interface for executing CRUD operations on BlackBox documents.
 */
@Repository
public interface BlackBoxRepository extends MongoRepository<BlackBox, String> {
}