package my.blog.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.blog.category.domain.Category;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private String name;
    private Long categoryNum; // 카테고리당 글 개수
    public CategoryDto(String categoryName, Long count) {
        this.name = categoryName;
        this.categoryNum = (count == null) ? 0 : count;
    }
}
