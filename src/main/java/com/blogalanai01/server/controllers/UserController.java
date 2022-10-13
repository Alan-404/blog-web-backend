package com.blogalanai01.server.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
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

import com.blogalanai01.server.dtos.user.RegisterDTO;
import com.blogalanai01.server.dtos.user.ResponseAddUserDTO;
import com.blogalanai01.server.dtos.user.ResponseGetUserDTO;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.User;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.hadoop.HadoopService;
import com.blogalanai01.server.services.media.MediaService;
import com.blogalanai01.server.services.user.UserService;



@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    private final AccountService accountService;
    private final Jwt jwt;
    private final HadoopService hadoopService;
    private final MediaService mediaService;
    
    
    public UserController(UserService userService, AccountService accountService, Jwt jwt, HadoopService hadoopService, MediaService mediaService){
        this.userService = userService;
        this.accountService = accountService;
        this.jwt = jwt;
        this.hadoopService = hadoopService;
        this.mediaService = mediaService;
    }


    @PostMapping("/add")
    public ResponseAddUserDTO addUser(@ModelAttribute RegisterDTO registerData){
        ResponseAddUserDTO response = new ResponseAddUserDTO(false, null, "Fail to Register User");
        User user = this.userService.addUser(registerData);
        if (user == null){
            response.setMessage("Existed Email");
            return response;
        }
        boolean addedImage = this.mediaService.saveMedia(registerData.getAvatar(), user.getId(), "avatars");
        //boolean addedImage = this.hadoopService.saveImage(registerData.getAvatar(), user.getId(), "users");
        if(addedImage == false){
            response.setMessage("Interanl Error Server");
            return response;
        }
        registerData.setUserId(user.getId());
        this.accountService.addAccount(registerData);
        response.setAllAttrs(true, user, "Register User Successfully");
        return response;
    }


    @GetMapping("/info")
    public ResponseGetUserDTO getUserByToken(HttpServletRequest httpServletRequest){
        ResponseGetUserDTO response = new ResponseGetUserDTO(false, null, false);
        try{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            if (authorizationHeader.startsWith("Bearer") == false && authorizationHeader.split(" ").length != 2){
                return response;
            }
            String token = authorizationHeader.split(" ")[1];
            if (token == null){
                return response;
            }
            String accountId = this.jwt.extractAccountId(token);
            if (accountId == null){
                return response;
            }
            Account account = this.accountService.getAccountById(accountId);
            if (account == null){
                return response;
            }
            User user = this.userService.getUserById(account.getUserId());
            

            response.setSuccess(true);
            response.setUser(user);
            response.setRole(account.getRole());
        }
        catch(Error err){
            err.printStackTrace();
        }

        return response;
    }

    @GetMapping("/information/{id}")
    public User getUserById(@PathVariable("id") String userId){
        User user = this.userService.getUserById(userId);

        return user;
    }


    @PutMapping("/edit")
    public User editInfoUser(@RequestBody User user, HttpServletRequest httpServletRequest){
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
        if (account == null){
            return null;
        }

        user.setId(account.getUserId());

        User checkUser = this.userService.getUserById(account.getUserId());
        if (checkUser == null){
            return null;
        }

        user.setEmail(checkUser.getEmail());

        return this.userService.editUser(user);
    }


    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getAvatarUser(@RequestParam String id){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(this.hadoopService.getImage(id, "users"));
    }
}
