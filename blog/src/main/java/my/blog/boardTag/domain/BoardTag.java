package my.blog.boardTag.domain;

import lombok.Getter;
import lombok.ToString;
import my.blog.board.domain.Board;
import my.blog.tag.domain.Tag;

import javax.persistence.*;

@ToString
@Getter
@Entity
public class BoardTag {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.EAGER)
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
    public static BoardTag newInstance(Board board, Tag tag) {
        BoardTag boardTag = new BoardTag();
        boardTag.setBoardAndTag(board, tag);

        return boardTag;
    }
}
