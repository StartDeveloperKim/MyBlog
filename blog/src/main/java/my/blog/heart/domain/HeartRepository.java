package my.blog.heart.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeartRepository extends JpaRepository<Heart, Long> , CustomHeartRepository{

    @Modifying(clearAutomatically = true)
    @Query("delete from Heart h where h.board.id = :boardId and h.user.id = :userId")
    void removeHeart(@Param("boardId") Long boarId,
                     @Param("userId") Long userId);

    Long countByBoard_Id(Long boardId);
}

