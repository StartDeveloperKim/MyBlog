package my.blog.category.domain;

import lombok.Getter;
import my.blog.board.domain.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SequenceGenerator(
        name = "CATEGORY_SEQ_GENERATOR",
        sequenceName = "CATEGORY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GENERATOR")
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String categoryName;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    private Category(String categoryName, Long parentCategoryId) {
        this.categoryName = categoryName;
        this.parentCategoryId = parentCategoryId;
    }

    protected Category() {
    }

    //==생성메서드==//
    public static Category from(String categoryName, Long parentCategoryId) {
        return new Category(categoryName, parentCategoryId);
    }

    //==업데이트 메서드==//
    public void edit(String categoryName) {
        this.categoryName = categoryName;
    }
}
