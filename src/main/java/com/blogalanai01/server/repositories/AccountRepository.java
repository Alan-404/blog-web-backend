package com.blogalanai01.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blogalanai01.server.models.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
    @Query(value = "{'userId': ?0}")
    Account getAccountByUserId(String userId);
    @Query(value = "{'id': ?0}")
    Account getAccountById(String id);
    
}
