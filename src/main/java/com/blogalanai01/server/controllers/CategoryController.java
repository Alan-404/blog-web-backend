package com.blogalanai01.server.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogalanai01.server.dtos.category.AddCategoryDTO;
import com.blogalanai01.server.dtos.category.ResponseAddCategoryDTO;
import com.blogalanai01.server.dtos.category.ResponseGetCategoriesDTO;
import com.blogalanai01.server.dtos.category.ResponseSizeCategoriesDTO;
import com.blogalanai01.server.middleware.Jwt;
import com.blogalanai01.server.models.Category;
import com.blogalanai01.server.services.account.AccountService;
import com.blogalanai01.server.services.category.CategoryService;
import com.blogalanai01.server.services.hadoop.HadoopService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final AccountService accountService;
    private final Jwt jwt;
    private final HadoopService hadoopService;

    public CategoryController(CategoryService categoryService, AccountService accountService, Jwt jwt, HadoopService hadoopService){
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.jwt = jwt;
        this.hadoopService = hadoopService;
    }


    @PostMapping("/add")
    public ResponseAddCategoryDTO addCategory(@ModelAttribute AddCategoryDTO category, HttpServletRequest httpServletRequest){
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

        boolean addedImage = this.hadoopService.saveImage(category.getImage(), addCategory.getId(), "categories");
        if (addedImage == false){
            response.setMessage("Error Internal Server Hadoop");
            return response;
        }

        response.setAllAttrs(addCategory, "Add Category Successfully", true);

        return response;
    }

    @GetMapping("/view")
    public ResponseGetCategoriesDTO getCategories(@RequestParam int number, @RequestParam int page){
        List<Category> categories = new ArrayList<Category>();
        ResponseGetCategoriesDTO response = new ResponseGetCategoriesDTO(true, categories);


        categories = this.categoryService.getCategories(number, page);
        response.setCategories(categories);
        return response;
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam String id){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(this.hadoopService.getImage(id, "categories"));
    }

    @GetMapping("/length")
    public ResponseSizeCategoriesDTO getSizeCategories(){
        ResponseSizeCategoriesDTO response = new ResponseSizeCategoriesDTO(0);
        response.setNumber(this.categoryService.getSizeCategories());
        return response;
    }

    @GetMapping("/all")
    public List<Category> getAllCategories(){
       

        return this.categoryService.getAllCategories();
    }
}
