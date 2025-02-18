package com.antonio.blog.service;

import com.antonio.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    public CategoryDto getCategoryById(Long id);

    public void deleteCategory(Long id);

    public List<CategoryDto> getAllCategories();
}
