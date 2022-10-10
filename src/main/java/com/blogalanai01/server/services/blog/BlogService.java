package com.blogalanai01.server.services.blog;

import java.util.List;

import com.blogalanai01.server.dtos.blog.CreateBlogDTO;
import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.models.Blog;

public interface BlogService {
    public Blog addBlog(CreateBlogDTO blog);
    public Blog getBlogById(String id);
    public Blog editStateBlogById(String id, BlogState state);
    public List<Blog> getBlogs(int number, int page);
    public int getTotalBlogs();
    public List<Blog> getBlogsByCategoryId(String categoryId);
}
