package my.blog.comments.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("select c from Comments c left join fetch c.user where c.board.id = :boardId order by c.id asc")
    List<Comments> findCommentsByBoardId(@Param("boardId") Long boardId);

    @Query("select count(c) from Comments c where c.board.id = :boardId")
    int getCommentsCountByBoardId(@Param("boardId") Long boardId);
}
