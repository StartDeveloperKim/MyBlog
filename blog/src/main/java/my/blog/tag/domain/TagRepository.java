package my.blog.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTagName(String tagName); // tag이름 중복 쿼리

    Tag findByTagName(String tagName);

    @Query("select t.id from Tag t where t.tagName=:tagName")
    Long findTagIdByTagName(@Param("tagName") String tagName);
}
