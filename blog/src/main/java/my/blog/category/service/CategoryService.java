package my.blog.category.service;

import my.blog.category.domain.Category;

public interface CategoryService {

    Long saveCategory(String name);

    void deleteCategory(String name);

    void updateCategory(String name);
    Category getCategoryByName(String categoryName);

}
