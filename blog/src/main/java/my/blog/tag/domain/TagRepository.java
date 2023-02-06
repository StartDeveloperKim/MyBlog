package my.blog.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>, CustomTagRepository {

    List<Tag> findTop30ByOrderByIdDesc();

    Tag findByTagName(String name);
    void deleteByTagName(String name);

}
