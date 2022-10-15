package my.blog.heart.domain;

import lombok.Getter;
import my.blog.board.domain.Board;
import my.blog.user.domain.User;

import javax.persistence.*;

@Getter
@Entity
@SequenceGenerator(
        name = "HEART_SEQ_GENERATOR",
        sequenceName = "HEART_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HEART_SEQ_GENERATOR")
    @Column(name = "heart_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
