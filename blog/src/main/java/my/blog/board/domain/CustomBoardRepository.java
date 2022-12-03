package my.blog.board.domain;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBoardRepository {
    List<Board> searchBoardByTitle(String word, Pageable pageable);

    Long searchBoardCount(String word);
}
