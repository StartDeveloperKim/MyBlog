package my.blog.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDto {

    private Long id;
    private Long parentCategoryId;
    private String name;
    private Long categoryNum;
}
