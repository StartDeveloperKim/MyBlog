package my.blog.boardTag.domain;

import my.blog.tag.domain.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

    /*@Query("select bt.tag from BoardTag bt join fetch bt.tag where bt.board.id=:id")
    List<Tag> findTagByBoardId(@Param("id") Long boardId);*/

    @Query("select b from BoardTag b " +
            "join b.tag " +
            "join fetch b.board " +
            "where b.tag.tagName=:tagName order by b.id desc")
    List<BoardTag> findBoardTagByTagTagName(Pageable pageable, @Param("tagName") String tagName);

    @Query("select count(b) from BoardTag b " +
            "join b.tag " +
            "where b.tag.tagName=:tagName")
    Long countBoardTagByTagName(@Param("tagName") String tagName);

    @Query("select b from BoardTag b join fetch b.tag where b.board.id=:id")
    List<BoardTag> findBoardTagsByBoardId(@Param("id") Long id);
}
