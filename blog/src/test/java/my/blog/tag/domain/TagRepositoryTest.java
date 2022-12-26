package my.blog.tag.domain;

import my.blog.factory.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static my.blog.factory.EntityFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void 전달된_리스트에_있는_태그들을_모두_조회한다() {
        //given
        Tag tag1 = tagRepository.save(newTagInstance(null, "태그1"));
        Tag tag2 = tagRepository.save(newTagInstance(null, "태그2"));
        List<String> tags = List.of("태그1", "태그2");

        //when
        List<Tag> result = tagRepository.findTags(tags);

        //then
        List<String> findTags = result.stream().map(Tag::getTagName).collect(Collectors.toList());
        assertAll(
                () -> assertEquals(tag1.getTagName(), result.get(0).getTagName()),
                () -> assertEquals(tag2.getTagName(), result.get(1).getTagName())
        );

    }
}