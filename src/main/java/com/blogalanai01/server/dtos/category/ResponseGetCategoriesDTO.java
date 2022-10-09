package com.blogalanai01.server.dtos.category;

import java.util.List;

import com.blogalanai01.server.models.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetCategoriesDTO {
    private boolean success;
    private List<Category> categories;
}
