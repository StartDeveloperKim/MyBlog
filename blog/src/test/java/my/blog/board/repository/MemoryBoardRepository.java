package my.blog.board.repository;

import my.blog.board.domain.Board;

import java.util.HashMap;
import java.util.Map;

public class MemoryBoardRepository{

    private final Map<Long, Board> boardTable = new HashMap<>();
    private Long boardId = 1L;

    public Long save(Board board) {
        boardTable.put(boardId, board);
        return boardId++;
    }
}
