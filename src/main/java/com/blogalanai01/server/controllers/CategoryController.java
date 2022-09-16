package com.blogalanai01.server.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.category.ResponseAddCategoryDTO;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Category;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.category.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final AccountService accountService;
    private final Jwt jwt;

    public CategoryController(CategoryService categoryService, AccountService accountService, Jwt jwt){
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.jwt = jwt;
    }


    @PostMapping("/add")
    public ResponseAddCategoryDTO addCategory(@RequestBody Category category, HttpServletRequest httpServletRequest){
        ResponseAddCategoryDTO response = new ResponseAddCategoryDTO();
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = authorizationHeader.split(" ")[1];
        if (token == null){
            response.setMessage("Invalid Token");
            return response;
        }
        String accountId = this.jwt.extractAccountId(token);
        if (accountId == null){
            response.setMessage("Invalid account id");
            return response;
        }
        boolean role = this.accountService.getRoleAccount(accountId);
        if (role == false){
            response.setMessage("You are not Admin");
            return response;
        }
        Category addCategory = this.categoryService.addCategory(category);
        if (addCategory == null){
            response.setMessage("Category Name existed");
            return response;
        }

        response.setAllAttrs(addCategory, "Add Category Successfully", true);

        return response;
    }
}
