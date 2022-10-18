package my.blog.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Category c where c.categoryName = :name")
    void deleteByCategoryName(@Param("name") String categoryName);

    Category findByCategoryName(String categoryName);
    Boolean existsByCategoryName(String categoryName); // 카테고리 존재여부
}
