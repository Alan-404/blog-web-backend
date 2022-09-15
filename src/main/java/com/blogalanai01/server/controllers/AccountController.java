package com.blogalanai01.server.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.account.LoginDTO;
import com.blogalanai01.server.dtos.account.ResponseLoginDTO;
import com.blogalanai01.server.dtos.account.changePasswordDTO;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.models.User;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.user.UserService;


@RestController
@RequestMapping("/account")
public class AccountController {


    private final AccountService accountService;
    private final UserService userService;
    private final Jwt jwt;

    public AccountController(AccountService accountService, UserService userService, Jwt jwt){
        this.accountService = accountService;
        this.userService = userService;
        this.jwt = jwt;
    }



    @GetMapping("/all")
    public List<Account> getAll(){
        return this.accountService.getAllAccounts();
    }
    

    @PostMapping("/login")
    public ResponseLoginDTO loginAccount(@RequestBody LoginDTO loginData){
        ResponseLoginDTO response = new ResponseLoginDTO(false, null);
        String email = loginData.getEmail();
        String password = loginData.getPassword();
        User checkUser = this.userService.findUserByEmail(email);

        if (checkUser == null){
            response.setAllAttrs(false, null);
            return response;
        }

        Account account = this.accountService.login(checkUser.getId(), password);

        if (account == null){
            response.setAllAttrs(false, null);
            return response;
        }

        String accessToken = this.jwt.generateToken(account.getId());

        response.setAllAttrs(true, accessToken);

        return response;
    }


    @PutMapping("/password/change")
    public boolean changePassword(HttpServletRequest httpServletRequest, @RequestBody changePasswordDTO requestData){
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String oldPassword = requestData.getOldPassword();
        String newPassword = requestData.getNewPassword();
        if(authorizationHeader == null){
            return false;
        }
        String token = authorizationHeader.split(" ")[1];
        System.out.println(token);
        if (token == null){
            return false;
        }
        String accountId = this.jwt.extractAccountId(token);
        if (accountId == null){
            return false;
        }
        Account account = this.accountService.getAccountById(accountId);
        if (account == null){
            return false;
        }
        return this.accountService.changePassword(account, oldPassword, newPassword);
    }

}
