package my.blog.category.service;

import my.blog.category.domain.Category;
import my.blog.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    void saveCategory(String name);
    void deleteCategory(String name);
    void updateCategory(String name);
    Category getCategoryByName(String categoryName);
    List<CategoryDto> getCategoryList();
}
