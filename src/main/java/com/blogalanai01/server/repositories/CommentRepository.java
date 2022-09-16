package com.blogalanai01.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.blogalanai01.server.models.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(value = "{'blogId': ?0}")
    public List<Comment> allCommentsByBlogId(String blogId);
}
