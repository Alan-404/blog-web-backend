package com.blogalanai01.server.dtos.category;

import com.blogalanai01.server.models.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAddCategoryDTO {
    private Category category = null;
    private String message = "";
    private boolean success = false;


    public void setAllAttrs(Category category, String message, boolean success){
        this.category = category;
        this.message = message;
        this.success =success;
    }
}
