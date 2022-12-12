package my.blog.category.domain;

import my.blog.category.dto.response.CategoryInfoDto;

import java.util.List;

public interface CustomCategoryRepository {

    List<CategoryInfoDto> getCategoryInfoDto();
}
