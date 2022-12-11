package my.blog.comments.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("select c from Comments c join fetch c.user where c.board.id = :boardId order by c.id asc")
    List<Comments> findCommentsByBoardId(@Param("boardId") Long boardId);

    @Query("select count(c) from Comments c where c.board.id = :boardId")
    int getCommentsCountByBoardId(@Param("boardId") Long boardId);

    //아래 두개의 comment 삭제 쿼리가 중복이 된다. QueryDSL의 동적쿼리로 해결할 수 있지 않을까?
    @Modifying(clearAutomatically = true)
    @Query("delete from Comments c where (c.id=:commentId or c.parentId=:commentId) and c.board.id=:boardId")
    void deleteParentComment(@Param("commentId") Long commentId, @Param("boardId") Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Comments c where c.id=:commentId and c.board.id=:boardId")
    void deleteChildComment(@Param("commentId") Long commentId, @Param("boardId") Long boardId);

}
