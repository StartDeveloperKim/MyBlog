package my.blog.category.domain;

import my.blog.category.dto.CategoryInfoDto;
import my.blog.category.dto.CategoryRespInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);

    @Query(name = "findCategoryDto", nativeQuery = true)
    List<CategoryInfoDto> findCategoryDto();
}
