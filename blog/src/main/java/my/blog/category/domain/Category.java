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

    private Category(String categoryName) {
        this.categoryName = categoryName;
    }

    protected Category() {
    }

    //==생성메서드==//
    public static Category from(String categoryName) {
        return new Category(categoryName);
    }

    //==업데이트 메서드==//
    public void edit(String categoryName) {
        this.categoryName = categoryName;
    }
}
