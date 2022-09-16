package com.blogalanai01.server.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.category.HandleBlogDTO;
import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Blog;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.blog.BlogService;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final Jwt jwt;
    private final BlogService blogService;
    private final AccountService accountService;


    public BlogController(Jwt jwt, BlogService blogService, AccountService accountService){
        this.jwt = jwt;
        this.blogService = blogService;
        this.accountService = accountService;
    }
    @PostMapping("/add")
    public Blog addBlog(@RequestBody Blog blog, HttpServletRequest httpServletRequest){
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.startsWith("Bearer") == false){
            return null;
        }
        String token = authorizationHeader.split(" ")[1];
        if (token == null){
            return null;
        }

        String accountId = this.jwt.extractAccountId(token);
        if (accountId == null){
            return null;
        }

        boolean role = this.accountService.getRoleAccount(accountId);
        if (role == true){
            blog.setBlogState(BlogState.ACTIVE);
        }
        else{
            blog.setBlogState(BlogState.PENDING);
        }

        return this.blogService.addBlog(blog);
    }

    @PutMapping("/handle")
    public Blog handleBlogState(@RequestBody HandleBlogDTO data, HttpServletRequest httpServletRequest){
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.startsWith("Bearer") == false){
            return null;
        }
        String token = authorizationHeader.split(" ")[1];
        if (token == null){
            return null;
        }

        String accountId = this.jwt.extractAccountId(token);
        if (accountId == null){
            return null;
        }

        boolean role = this.accountService.getRoleAccount(accountId);
        if (role == false){
            return null;
        }

        return this.blogService.editStateBlogById(data.getId(), data.getBlogState());
    }
}
