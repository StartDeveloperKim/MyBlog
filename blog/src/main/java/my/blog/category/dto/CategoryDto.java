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
    public CategoryDto(Category category) {
        this.name = category.getCategoryName();
    }
}
