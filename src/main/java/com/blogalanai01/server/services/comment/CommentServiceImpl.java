package com.blogalanai01.server.services.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogalanai01.server.models.Comment;
import com.blogalanai01.server.repositories.CommentRepository;


@Service
public class CommentServiceImpl implements CommentService {
    
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Comment comment){
        return this.commentRepository.save(comment);
    }

    @Override
    public List<Comment> allCommentsByBlogId(String blogId){
        return this.commentRepository.allCommentsByBlogId(blogId);
    }

}
