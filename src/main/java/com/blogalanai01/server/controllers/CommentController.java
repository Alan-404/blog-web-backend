package com.blogalanai01.server.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.comment.InfoCommentDTO;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.Comment;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.comment.CommentService;
import com.blogalanai01.server.services.user.UserService;



@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final Jwt jwt;
    private final AccountService accountService;
    private final UserService userService;

    public CommentController(CommentService commentService, Jwt jwt, AccountService accountService, UserService userService){
        this.commentService = commentService;
        this.jwt = jwt;
        this.accountService = accountService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment, HttpServletRequest httpServletRequest){
        System.out.println("OK");
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

    @GetMapping("/reply/{id}")
    public List<InfoCommentDTO> getRepliesComment(@PathVariable("id") String id){
        List<InfoCommentDTO> lstComments = new ArrayList<>();

        List<Comment> comments = this.commentService.getRepliesComment(id);

        for (Comment comment: comments){
            InfoCommentDTO info = new InfoCommentDTO();
            info.setBlogId(comment.getBlogId());
            info.setContent(comment.getContent());
            info.setId(comment.getId());
            info.setNumReplies(0);
            info.setReply(comment.getReply());
            info.setUser(this.userService.getUserById(comment.getUserId()));
            info.setNumReplies(this.commentService.getNumReplies(info.getId()));
            lstComments.add(info);
        }

        return lstComments;
    }

    @GetMapping("/all/{id}")
    public List<InfoCommentDTO> getAllComments(@PathVariable("id") String id){
        List<InfoCommentDTO> lstComments = new ArrayList<>();

        List<Comment> comments = this.commentService.allCommentsByBlogId(id);

        for (Comment comment: comments){
            InfoCommentDTO info = new InfoCommentDTO();
            info.setBlogId(comment.getBlogId());
            info.setContent(comment.getContent());
            info.setId(comment.getId());
            info.setNumReplies(0);
            info.setReply(comment.getReply());
            info.setUser(this.userService.getUserById(comment.getUserId()));
            info.setNumReplies(this.commentService.getNumReplies(info.getId()));
            lstComments.add(info);
        }

        return lstComments;
    }

}
