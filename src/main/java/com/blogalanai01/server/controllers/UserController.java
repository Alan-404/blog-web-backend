package com.blogalanai01.server.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.user.RegisterDTO;
import com.blogalanai01.server.dtos.user.ResponseAddUserDTO;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.User;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.user.UserService;



@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    private final AccountService accountService;
    private final Jwt jwt;
    
    
    public UserController(UserService userService, AccountService accountService, Jwt jwt){
        this.userService = userService;
        this.accountService = accountService;
        this.jwt = jwt;
    }


    @PostMapping("/add")
    public ResponseAddUserDTO addUser(@RequestBody RegisterDTO registerData){
        ResponseAddUserDTO response = new ResponseAddUserDTO(false, null, "Fail to Register User");
        System.out.println(registerData.getAvatar());
        User user = this.userService.addUser(registerData);
        if (user == null){
            response.setMessage("Existed Email");
            return response;
        }
        registerData.setUserId(user.getId());
        this.accountService.addAccount(registerData);
        response.setAllAttrs(true, user, "Register User Successfully");
        return response;
    }


    @GetMapping("/info")
    public User getUserByToken(HttpServletRequest httpServletRequest){
        try{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = authorizationHeader.split(" ")[1];
            if (token == null){
                return null;
            }
            String accountId = this.jwt.extractAccountId(token);
            if (accountId == null){
                return null;
            }
            Account account = this.accountService.getAccountById(accountId);
            if (account == null){
                return null;
            }
            User user = this.userService.getUserById(account.getUserId());
            return user;
        }
        catch(Error err){
            return null;
        }
    }

    @GetMapping("/information/{id}")
    public User getUserById(@PathVariable("id") String userId){
        User user = this.userService.getUserById(userId);

        return user;
    }

}
