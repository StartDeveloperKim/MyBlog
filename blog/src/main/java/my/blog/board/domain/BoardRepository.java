package my.blog.board.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    @Query("select b from Board b order by b.id desc")
    List<Board> findBoardsOrderByIdDesc(Pageable pageable);

    @Query("select b from Board b " +
            "where b.category.id=:id or b.category.parentCategoryId=:id order by b.id desc ")
    List<Board> findByCategoryId(@Param("id") Long categoryId, Pageable pageable);

    @Query("select count(b) from Board b where b.category.id = :categoryId")
    Long getBoardCountByCategory(@Param("categoryId") Long categoryId);

    @Query("select count(b) from Board b " +
            "where b.category.id=:id or b.category.parentCategoryId=:id")
    Long getBoardCountByCategoryId(@Param("id") Long categoryId);

    List<Board> findTop6ByOrderByCreateDateDesc();
}
