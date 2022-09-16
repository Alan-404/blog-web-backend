package com.blogalanai01.server.services.category;

import com.blogalanai01.server.models.Category;

public interface CategoryService {
    public Category getCategoryByName(String name);
    public Category addCategory(Category category);
}
