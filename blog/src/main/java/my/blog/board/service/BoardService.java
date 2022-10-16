package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;

import java.util.List;

public interface BoardService {

    Long writeBoard(BoardRegister boardRegister);

    void editBoard(BoardUpdate boardUpdate);

    void deleteBoard(Long boardId);

    void addHit(Long boardId);

    Board getBoard(Long boardId);

    List<Board> getBoardList();
}
