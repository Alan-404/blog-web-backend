package com.blogalanai01.server.services.comment;

import java.util.List;

import com.blogalanai01.server.models.Comment;

public interface CommentService {
    public Comment addComment(Comment comment);
    public List<Comment> allCommentsByBlogId(String blogId);
}
