package com.blogalanai01.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blogalanai01.server.models.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
    @Query(value = "{'id': ?0}")
    public Blog getBlogById(String id);
}
