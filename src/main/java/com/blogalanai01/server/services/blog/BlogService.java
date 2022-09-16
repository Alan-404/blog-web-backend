package com.blogalanai01.server.services.blog;

import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.models.Blog;

public interface BlogService {
    public Blog addBlog(Blog blog);
    public Blog getBlogById(String id);
    public Blog editStateBlogById(String id, BlogState state);
}
