package my.blog.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTagName(String tagName);

    @Query("select t.id from Tag t where t.tagName=:tagName")
    Long findTagIdByTagName(@Param("tagName") String tagName);
}
