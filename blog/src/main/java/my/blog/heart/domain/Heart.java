package my.blog.heart.domain;

import lombok.Getter;
import my.blog.board.domain.Board;
import my.blog.user.domain.User;

import javax.persistence.*;

@Getter
@Entity
public class Heart {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "heart_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    protected Heart() {
    }

    //==생성메서드==//
    public static Heart of(User user, Board board) {
        Heart heart = new Heart();
        heart.setUserAndBoard(user, board);
        return heart;
    }

    //==연관관계 편의 메서드==//
    private void setUserAndBoard(User user, Board board) {
        this.board = board;
        this.user = user;
        user.getHearts().add(this);
    }
}
