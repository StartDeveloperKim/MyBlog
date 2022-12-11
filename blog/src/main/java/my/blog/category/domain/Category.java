package my.blog.category.domain;

import lombok.Getter;
import my.blog.category.dto.response.CategoryInfoDto;

import javax.persistence.*;

@Getter
@SqlResultSetMapping(
        name = "mappingCategoryDto",
        classes = @ConstructorResult(
                targetClass = CategoryInfoDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "parentCategoryId", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "categoryNum", type = Long.class)
                }
        )
)
@NamedNativeQuery(
        name = "findCategoryDto",
        query = "SELECT C.CATEGORY_ID AS id, C.PARENT_CATEGORY_ID AS parentCategoryId, CATEGORY_NAME AS name , COUNT AS categoryNum " +
                "FROM (SELECT CATEGORY_ID, COUNT(CATEGORY_ID) AS COUNT " +
                "FROM BOARD GROUP BY CATEGORY_ID) AS SQ " +
                "RIGHT OUTER JOIN CATEGORY AS C ON SQ.CATEGORY_ID = C.CATEGORY_ID " +
                "WHERE C.CATEGORY_NAME != 'total' " +
                "ORDER BY C.CATEGORY_ID ASC",
        resultSetMapping = "mappingCategoryDto"
)
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
