package my.blog.category.dto;

import my.blog.category.dto.response.CategoryInfoDto;
import my.blog.category.dto.response.CategoryLayoutDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchicalCategory {


    public static Map<Long, CategoryLayoutDto> from(List<CategoryInfoDto> categoryDto) {
        Map<Long, CategoryLayoutDto> categoryResult = new HashMap<>();

        for (CategoryInfoDto category : categoryDto) {
            if (category.getParentCategoryId() == null) {
                categoryResult.put(category.getId(), new CategoryLayoutDto(
                        category.getId(), category.getName(), category.getCategoryNum()));
            } else {
                categoryResult.get(category.getParentCategoryId())
                        .addCategoryNum(category.getCategoryNum());
                categoryResult.get(category.getParentCategoryId())
                        .getChildCategory()
                        .add(new CategoryLayoutDto(category.getId(), category.getName(), category.getCategoryNum()));
            }
        }

        return categoryResult;
    }
}
