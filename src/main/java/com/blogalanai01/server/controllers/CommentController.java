package com.blogalanai01.server.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.Comment;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.comment.CommentService;



@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final Jwt jwt;
    private final AccountService accountService;

    public CommentController(CommentService commentService, Jwt jwt, AccountService accountService){
        this.commentService = commentService;
        this.jwt = jwt;
        this.accountService = accountService;
    }


    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment, HttpServletRequest httpServletRequest){
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader == null){
            return null;
        }

        String token = authorizationHeader.split(" ")[1];

        if (token == null){
            return null;
        }

        String accountId = this.jwt.extractAccountId(token);
        Account account = this.accountService.getAccountById(accountId);
        if (account == null){
            return null;
        }

        comment.setUserId(account.getUserId());

        return this.commentService.addComment(comment);
        
    }
}
