package my.blog.category.service;

import my.blog.category.domain.Category;
import my.blog.category.dto.CategoryAddDto;
import my.blog.category.dto.CategoryDto;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    Long saveCategory(CategoryAddDto categoryAddDto);
    void deleteCategory(Long id);
    void updateCategory(String name);
    Category getCategoryByName(String categoryName);
    Map<Long, CategoryDto> getCategoryList();
}
