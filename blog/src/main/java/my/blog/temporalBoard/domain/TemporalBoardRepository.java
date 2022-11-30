package my.blog.temporalBoard.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporalBoardRepository extends JpaRepository<TemporalBoard, Long> {
    TemporalBoard findTop1ByOrderByIdDesc();
}
