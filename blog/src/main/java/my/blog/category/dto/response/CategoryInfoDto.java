package my.blog.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDto {

    private Long id;
    private Long parentCategoryId;
    private String name;
    private Long categoryNum;
}
