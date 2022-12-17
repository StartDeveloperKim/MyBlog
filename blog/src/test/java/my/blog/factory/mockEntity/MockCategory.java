package my.blog.factory.mockEntity;

import lombok.Builder;
import lombok.Getter;
import my.blog.category.domain.Category;

@Getter
public class MockCategory extends Category {

    private Long id;
    private String categoryName;
    private Long parentId;

    @Builder
    public MockCategory(Long id, String categoryName, Long parentId) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
    }
}
