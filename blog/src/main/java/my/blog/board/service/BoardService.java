package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.Paging;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    Long writeBoardWithTag(BoardRegister boardRegister, List<String> tags);

    Long writeBoard(BoardRegister boardRegister);

    void editBoard(BoardUpdate boardUpdate);

    void deleteBoard(Long boardId);

    void addHit(Long boardId);

    Board getBoard(Long boardId);

    List<BoardResponse> getBoardList(int page, int size);

    List<BoardResponse> getBoardListRecent();

    Long getBoardCount();
}
