package com.blogalanai01.server.services.category;

import org.springframework.stereotype.Service;

import com.blogalanai01.server.models.Category;
import com.blogalanai01.server.repositories.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category addCategory(Category category){
        Category checkCategory = getCategoryByName(category.getName());
        if (checkCategory == null){
            return null;
        }
        this.categoryRepository.save(category);
        return category;
    }

    @Override
    public Category getCategoryByName(String name){
        return this.categoryRepository.getCategoryByName(name);
    }
}
