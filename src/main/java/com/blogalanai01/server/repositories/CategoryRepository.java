package com.blogalanai01.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blogalanai01.server.models.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
    @Query(value = "{'name': ?0}")
    public Category getCategoryByName(String name);
}
