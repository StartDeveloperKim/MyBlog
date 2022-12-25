package my.blog.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import my.blog.category.domain.Category;

@ToString
@Getter
@NoArgsConstructor
public class CategoryAddDto {

    private String categoryName;
    private Long parentCategoryId;

    public Category toEntity() {
        return Category.newInstance(this.categoryName, this.parentCategoryId);
    }
}
