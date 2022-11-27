package my.blog.temporalBoard.domain;

import my.blog.temporalBoard.dto.TemporalBoardReq;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TemporalBoardRepositoryTest {

    @Autowired
    TemporalBoardRepository temporalBoardRepository;

    @Test
    void 최근_임시저장_글_가져오기_테스트() {
        //given
        TemporalBoardReq temporalBoardReq = new TemporalBoardReq("테스트1", "테스트글입니다.", null, null, 2L);
        TemporalBoard savedEntity = temporalBoardRepository.save(TemporalBoard.of(temporalBoardReq));

        //when
        TemporalBoard findEntity = temporalBoardRepository.findTop1ByOrderByIdDesc();

        //then
        Assertions.assertThat(savedEntity.getId()).isEqualTo(findEntity.getId());
    }

}