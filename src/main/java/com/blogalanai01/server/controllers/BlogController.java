package com.blogalanai01.server.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.blog.ShowBlogDTO;
import com.blogalanai01.server.dtos.category.HandleBlogDTO;
import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.Blog;
import com.blogalanai01.server.models.Comment;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.blog.BlogService;
import com.blogalanai01.server.services.comment.CommentService;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final Jwt jwt;
    private final BlogService blogService;
    private final AccountService accountService;
    private final CommentService commentService;


    public BlogController(Jwt jwt, BlogService blogService, AccountService accountService, CommentService commentService){
        this.jwt = jwt;
        this.blogService = blogService;
        this.accountService = accountService;
        this.commentService = commentService;
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
        Account account = this.accountService.getAccountById(accountId);
        if (account.getRole() == true){
            blog.setBlogState(BlogState.ACTIVE);
        }
        else{
            blog.setBlogState(BlogState.PENDING);
        }

        blog.setUserId(account.getUserId());


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

    @GetMapping("/show/{id}")
    public ShowBlogDTO showBlog(@PathVariable("id") String id){
        ShowBlogDTO response = new ShowBlogDTO();
        Blog blog = this.blogService.getBlogById(id);
        if (blog == null){
            return response;
        }

        List<Comment> comments = this.commentService.allCommentsByBlogId(id);

        response.setBlog(blog);
        response.setComments(comments);

        return response;
    }
}
