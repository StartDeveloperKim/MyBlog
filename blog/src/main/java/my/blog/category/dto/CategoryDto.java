package my.blog.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private Long categoryNum; // 카테고리당 글 개수
    private List<CategoryDto> childCategory = new ArrayList<>();
    public CategoryDto(Long categoryId, String categoryName, Long count) {
        this.id = categoryId;
        this.name = categoryName;
        this.categoryNum = (count == null) ? 0 : count;
    }
}
