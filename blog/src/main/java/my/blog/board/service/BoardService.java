package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {

    Long writeBoard(BoardRegister boardRegister, List<String> tags);

    void editBoard(BoardUpdate boardUpdate);

    void deleteBoard(Long boardId);

    void addHit(Long boardId);

    Board getBoard(Long boardId);

    List<BoardResponse> getBoardList();

    List<BoardResponse> getBoardListRecent();

    Long getBoardCount();
}
