package com.blogalanai01.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blogalanai01.server.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(value="{'email': ?0}")
    User getUserByEmmail(String email);
    @Query(value = "{'id': ?0}")
    User getUserById(String id);
}
