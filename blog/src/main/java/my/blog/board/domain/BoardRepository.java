package my.blog.board.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b join fetch b.boardTags bt join fetch bt.tag where b.id=:id")
    Optional<Board> findByIdForBoardTag(@Param("id") Long boardId);

    @Query("select b from Board b " +
            "order by b.id desc")
    Slice<Board> findByOrderByIdDesc(Pageable pageable);

    @Query("select b from Board b where b.category.categoryName=:categoryName order by b.id desc")
    Slice<Board> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    @Query("select b from Board b " +
            "where b.category.id=:id or b.category.parentCategoryId=:id order by b.id desc ")
    Slice<Board> findByCategoryId(@Param("id") Long categoryId, Pageable pageable);

    @Query("select count(b) from Board b where b.category.id = :categoryId")
    Long getBoardCountByCategory(@Param("categoryId") Long categoryId);

    @Query("select count(b) from Board b")
    Long getAllBoardCount();

    @Query("select count(b) from Board b where b.category.categoryName = :categoryName")
    Long getBoardCountByCategoryName(@Param("categoryName") String categoryName);

    @Query("select count(b) from Board b " +
            "where b.category.id=:id or b.category.parentCategoryId=:id")
    Long getBoardCountByCategoryId(@Param("id") Long categoryId);

    List<Board> findTop6ByOrderByCreateDateDesc();
}
