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
}
