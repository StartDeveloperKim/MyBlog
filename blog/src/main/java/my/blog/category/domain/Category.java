package my.blog.category.domain;

import lombok.Getter;

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

    protected Category() {
    }

    //==생성메서드==//
    public static Category newInstance(String categoryName, Long parentCategoryId) {
        Category category = new Category();
        category.categoryName = categoryName;
        category.parentCategoryId = parentCategoryId;
        return category;
    }

    //==업데이트 메서드==//
    public void edit(String categoryName) {
        this.categoryName = categoryName;
    }
}
