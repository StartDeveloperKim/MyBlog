package my.blog.category.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchicalCategory {

    /**
     * 카테고리정보를 계층형 카테고리로 변환한다.
     * @param categoryDto
     * @return
     */
    public static Map<Long, CategoryLayoutDto> from(List<CategoryInfoDto> categoryDto) {
        Map<Long, CategoryLayoutDto> categoryResult = new HashMap<>();

        for (CategoryInfoDto category : categoryDto) {
            if (category.getParentCategoryId() == null) {
                categoryResult.put(category.getId(), new CategoryLayoutDto(
                        category.getId(), category.getName(), category.getCategoryNum()));
            } else {
                categoryResult.get(category.getParentCategoryId())
                        .getChildCategory()
                        .add(new CategoryLayoutDto(category.getId(), category.getName(), category.getCategoryNum()));
            }
        }

        return categoryResult;
    }
}
