package my.blog.boardTag.domain;

import lombok.Getter;
import my.blog.board.domain.Board;
import my.blog.tag.domain.Tag;

import javax.persistence.*;

@Getter
@Entity
@SequenceGenerator(
        name = "BOARDTAG_SEQ_GENERATOR",
        sequenceName = "BOARDTAG_SEQ",
        initialValue = 1,
        allocationSize = 1)
public class BoardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARDTAG_SEQ_GENERATOR")
    @Column(name = "board_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    protected BoardTag() {
    }

    //==연관관계 편의 메서드==//
    private void setBoardAndTag(Board board, Tag tag) {
        this.board = board;
        this.tag = tag;
        board.getBoardTags().add(this);
        tag.getBoardTags().add(this);
    }

    //==생성 메서드==//
    public static BoardTag from(Board board, Tag tag) {
        BoardTag boardTag = new BoardTag();
        boardTag.setBoardAndTag(board, tag);
        
        return boardTag;
    }
}
