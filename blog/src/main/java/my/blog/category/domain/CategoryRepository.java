package my.blog.category.domain;

import my.blog.category.dto.CategoryDto;
import my.blog.category.dto.CategoryRespInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Category c where c.categoryName = :name")
    void deleteByCategoryName(@Param("name") String categoryName);

    Category findByCategoryName(String categoryName);
    Boolean existsByCategoryName(String categoryName); // 카테고리 존재여부

    @Query(value = "SELECT CATEGORY_NAME AS name , COUNT AS categoryNum " +
            "FROM (SELECT CATEGORY_ID, COUNT(CATEGORY_ID) AS COUNT " +
            "FROM BOARD GROUP BY CATEGORY_ID) AS SQ " +
            "RIGHT OUTER JOIN CATEGORY AS C ON SQ.CATEGORY_ID = C.CATEGORY_ID ORDER BY C.CATEGORY_ID ASC", nativeQuery = true)
    List<CategoryRespInterface> findCategoryDto();
}
