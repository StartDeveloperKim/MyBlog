package my.blog.factory.mockEntity;

import lombok.Getter;
import my.blog.board.domain.Board;
import my.blog.boardTag.domain.BoardTag;
import my.blog.tag.domain.Tag;

@Getter
public class MockBoardTag extends BoardTag {

    private Long id;
    private Board board;
    private Tag tag;

    public MockBoardTag(Long id, Board board, Tag tag) {
        this.id = id;
        this.board = board;
        this.tag = tag;
    }
}
