package my.blog.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository {

    @Query("select c from Category c where c.categoryName=:categoryName and c.parentCategoryId is null")
    Category findByNameAndParentIdIsNull(@Param("categoryName") String name);

    @Query("select c from Category p, Category c " +
            "where (p.id=c.parentCategoryId) and (p.categoryName=:parentName and c.categoryName=:childName)")
    Category findByNameAndParentName(@Param("parentName") String parentName,
                                     @Param("childName") String childName);


    Category findByCategoryName(String categoryName);

    @Query("select count(c) > 0 from Category c " +
            "where c.parentCategoryId is null and c.categoryName=:name")
    boolean existsByCategoryName(@Param("name") String categoryName);

    @Query("select count(c)>0 from Category c " +
            "where c.parentCategoryId=:parentId and c.categoryName=:name")
    boolean existByNameAndParentId(@Param("parentId")Long parentCategoryId,
                                   @Param("name") String categoryName);

}
