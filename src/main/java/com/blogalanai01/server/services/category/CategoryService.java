package com.blogalanai01.server.services.category;

import java.util.List;

import com.blogalanai01.server.dtos.category.AddCategoryDTO;
import com.blogalanai01.server.models.Category;

public interface CategoryService {
    public Category getCategoryByName(String name);
    public Category addCategory(AddCategoryDTO category);
    public List<Category> getCategories(int number, int page);
    public int getSizeCategories();
    public List<Category> getAllCategories();
}
