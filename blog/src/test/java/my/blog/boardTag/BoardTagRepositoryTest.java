package my.blog.boardTag;

import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.tag.dto.TagCountResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardTagRepositoryTest {
    
    @Autowired
    private BoardTagRepository boardTagRepository;
    
    @Test
    void 많이걸린_태그순으로_태그이름과_개수를_조회한다() {
        //given, when
        List<TagCountResponse> result = boardTagRepository.findTagCountDtoOrderByCount(10L);
        //then
        Assertions.assertThat(result.size()).isEqualTo(10);
    }
}
