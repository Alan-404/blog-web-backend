package com.blogalanai01.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.blogalanai01.server.models.Blog;

public interface BlogRepository extends MongoRepository<Blog, String> {
    
}
