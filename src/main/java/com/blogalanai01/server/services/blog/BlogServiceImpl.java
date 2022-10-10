package com.blogalanai01.server.services.blog;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.blogalanai01.server.dtos.blog.CreateBlogDTO;
import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.models.Blog;
import com.blogalanai01.server.repositories.BlogRepository;

import org.modelmapper.ModelMapper;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    public BlogServiceImpl(BlogRepository blogRepository, ModelMapper modelMapper){
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Blog addBlog(CreateBlogDTO blogData){
        Blog blog = this.modelMapper.map(blogData, Blog.class);
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

    @Override
    public List<Blog> getBlogs(int number, int page){
        List<Blog> lst = this.blogRepository.findAll();

        int start = (page - 1)*number;
        int end = start + number;

        if (end > lst.size()){
            end = lst.size() ;
        }

        return lst.subList(start, end);
    }

    @Override
    public int getTotalBlogs(){
        return this.blogRepository.findAll().size();
    }

    @Override
    public List<Blog> getBlogsByCategoryId(String categoryId){
        List<Blog> blogs = this.blogRepository.findAll();
        List<Blog> blogCategory = new ArrayList<Blog>();
        for (Blog blog : blogs) {
            for (String category: blog.getCategoryId()) {
                if (category.equals(categoryId)){
                    blogCategory.add(blog);
                }
            }

        }

        return blogCategory;
    }
}
