package com.blogalanai01.server.services.blog;


import org.springframework.stereotype.Service;

import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.models.Blog;
import com.blogalanai01.server.repositories.BlogRepository;



@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;


    public BlogServiceImpl(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }


    @Override
    public Blog addBlog(Blog blog){
        return this.blogRepository.save(blog);
    }

    @Override
    public Blog getBlogById(String id){
        return this.blogRepository.getBlogById(id);
    }

    @Override
    public Blog editStateBlogById(String id, BlogState state){
        Blog blog = getBlogById(id);
        blog.setBlogState(state);

        return this.blogRepository.save(blog);
    }
}
