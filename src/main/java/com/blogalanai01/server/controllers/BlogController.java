package com.blogalanai01.server.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.blogalanai01.server.dtos.blog.CreateBlogDTO;
import com.blogalanai01.server.dtos.blog.ResponseViewBlogs;
import com.blogalanai01.server.dtos.blog.ShowBlogDTO;
import com.blogalanai01.server.dtos.category.HandleBlogDTO;
import com.blogalanai01.server.enums.BlogState;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.Blog;
import com.blogalanai01.server.models.Comment;
import com.blogalanai01.server.models.User;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.blog.BlogService;
import com.blogalanai01.server.services.comment.CommentService;
import com.blogalanai01.server.services.hadoop.HadoopService;
import com.blogalanai01.server.services.user.UserService;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final Jwt jwt;
    private final BlogService blogService;
    private final AccountService accountService;
    private final CommentService commentService;
    private final UserService userService;
    private final HadoopService hadoopService;


    public BlogController(Jwt jwt, BlogService blogService, AccountService accountService, CommentService commentService, UserService userService, HadoopService hadoopService){
        this.jwt = jwt;
        this.hadoopService = hadoopService;
        this.blogService = blogService;
        this.accountService = accountService;
        this.commentService = commentService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public Blog addBlog(@ModelAttribute CreateBlogDTO blog, HttpServletRequest httpServletRequest){
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


        Blog addedBlog = this.blogService.addBlog(blog);
        
        boolean addThumnail = this.hadoopService.saveImage(blog.getThumnail(), addedBlog.getId(), "blogs/thumnail");

        if (addThumnail){
            return addedBlog;
        }
        return null;
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

        User user = this.userService.getUserById(blog.getUserId());

        response.setAuthor(user);

        List<Comment> comments = this.commentService.allCommentsByBlogId(id);

        response.setBlog(blog);
        response.setComments(comments);

        return response;
    }

    @GetMapping("/view")
    public ResponseViewBlogs getBlogs(@RequestParam int number, @RequestParam int page){
        ResponseViewBlogs response = new ResponseViewBlogs();
        response.setBlogs(this.blogService.getBlogs(number, page));
        return response;
    }

    @GetMapping("/length")
    public int getSizeBlogs(){
        return this.blogService.getTotalBlogs();
    }

    @GetMapping("/thumnail")
    public ResponseEntity<byte[]> getThumnail(@RequestParam String id){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(this.hadoopService.getImage(id, "blogs/thumnail"));
    }

    @GetMapping("/category/{id}")
    public List<Blog> getBlogsByCategory(@PathVariable("id") String id){
        return this.blogService.getBlogsByCategoryId(id);
    }

}
