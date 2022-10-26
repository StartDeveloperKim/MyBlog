package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.Paging;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    Long writeBoardWithTag(BoardRegister boardRegister, List<String> tags, Long userId);

    Long writeBoard(BoardRegister boardRegister, Long userId);

    void editBoard(BoardUpdate boardUpdate);

    void deleteBoard(Long boardId);

    void addHit(Long boardId);

    Board getBoard(Long boardId);

    List<BoardResponse> getBoardList(int page, int size, String category, String step);

    List<BoardResponse> getBoardListRecent();

    Long getBoardCount();

    Long getBoardCountByCategory(String category);
}
