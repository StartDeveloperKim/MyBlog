package my.blog.category.domain;

import lombok.Getter;
import my.blog.category.dto.response.CategoryInfoDto;

import javax.persistence.*;

@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
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
