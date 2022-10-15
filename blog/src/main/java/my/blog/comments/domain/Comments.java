package my.blog.comments.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.domain.Board;
import my.blog.user.domain.User;

import javax.persistence.*;

@Getter
@Entity
@SequenceGenerator(
        name = "COMMENTS_SEQ_GENERATOR",
        sequenceName = "COMMENTS_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_SEQ_GENERATOR")
    @Column(name = "comments_id", nullable = false)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
