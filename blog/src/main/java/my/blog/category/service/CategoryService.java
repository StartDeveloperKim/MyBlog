package my.blog.category.service;

import my.blog.category.domain.Category;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.response.CategoryLayoutDto;

import java.util.Map;

public interface CategoryService {

    Long saveCategory(Category category);
    void deleteCategory(Long id);
    void updateCategory(String name);
    Category getCategoryByName(String categoryName);
    Map<Long, CategoryLayoutDto> getCategoryList();
}
