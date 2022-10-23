package my.blog.tag.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTagName(String tagName); // tag이름 중복 쿼리

    Tag findByTagName(String tagName);
}
