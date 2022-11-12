package my.blog.category.domain;

import my.blog.category.dto.CategoryInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
    boolean existsByCategoryName(String categoryName);

    @Query("select count(c)>0 from Category c " +
            "where c.parentCategoryId=:parentId and c.categoryName=:name")
    boolean existByNameAndParentId(@Param("parentId")Long parentCategoryId,
                                   @Param("name") String categoryName);

    @Query(name = "findCategoryDto", nativeQuery = true)
    List<CategoryInfoDto> findCategoryDto();
}
