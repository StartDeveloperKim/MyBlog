package my.blog.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private String status;

    public CategoryResponseDto(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
