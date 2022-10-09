package com.blogalanai01.server.services.category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blogalanai01.server.dtos.category.AddCategoryDTO;
import com.blogalanai01.server.models.Category;
import com.blogalanai01.server.repositories.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private ModelMapper modelmapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelmapper){
        this.categoryRepository = categoryRepository;
        this.modelmapper = modelmapper;
    }
    @Override
    public Category addCategory(AddCategoryDTO categoryData){
        Category category = this.modelmapper.map(categoryData, Category.class);
        Category checkCategory = getCategoryByName(category.getName());
        if (checkCategory != null){
            return null;
        }
        this.categoryRepository.save(category);
        return category;
    }

    @Override
    public Category getCategoryByName(String name){
        return this.categoryRepository.getCategoryByName(name);
    }

    @Override
    public List<Category> getCategories(int number, int page){
        List<Category> categories = this.categoryRepository.findAll();
        
        int start = (page-1)*number;

        int end = start + number;

        if (start > categories.size()){
            categories.removeAll(categories);
            return categories;
        }

        if (end > categories.size()){
            end = categories.size();
        }

        

        return categories.subList(start, end);
    }

    @Override
    public int getSizeCategories(){
        List<Category> categories = this.categoryRepository.findAll();

        return categories.size();
    }

    @Override
    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

}
