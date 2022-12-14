package my.blog.board.service;

import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;

import java.util.List;

public interface BoardService {

    Long writeBoard(BoardRegister boardRegister, Long userId);

    void editBoard(BoardUpdate boardUpdate, Long boardId, List<String> tags);

    void deleteBoard(Long boardId);

    void addHit(Long boardId);
}
