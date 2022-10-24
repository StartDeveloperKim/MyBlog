package my.blog.comments.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("select c from Comments c where c.board.id = :boardId order by c.createDate desc")
    List<Comments> findCommentsByBoardId(@Param("boardId") Long boardId);
}
