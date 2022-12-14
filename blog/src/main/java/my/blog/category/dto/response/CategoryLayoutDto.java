package my.blog.category.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class CategoryLayoutDto {

    private Long id;
    private String name;
    private Long categoryNum; // 카테고리당 글 개수
    private List<CategoryLayoutDto> childCategory = new ArrayList<>();
    public CategoryLayoutDto(Long categoryId, String categoryName, Long count) {
        this.id = categoryId;
        this.name = categoryName;
        this.categoryNum = (count == null) ? 0 : count;
    }

    public void addCategoryNum(Long count) {
        this.categoryNum += (count == null) ? 0 : count;
    }
}
