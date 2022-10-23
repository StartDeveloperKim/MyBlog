package my.blog.board.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Slice<Board> findByOrderById(Pageable pageable);

    // 스프링데이터 JPA의 remove를 사용한다면 엔티티가 필요하다 따라서 쿼리를 직접 날렸다.
    // Cascade 옵션을 설정했기에 연관된 엔티티들이 모두 삭제된다.
    @Modifying(clearAutomatically = true)
    @Query("delete from Board b where b.id = :id")
    void deleteById(@Param("id") Long boardId);

    @Query("select count(b) from Board b where b.category.categoryName = :categoryName")
    Long getBoardCountByCategory(String categoryName);

    @Query("select count(b) from Board b")
    Long getAllBoardCount();

    List<Board> findTop6ByOrderByCreateDateDesc();
}
