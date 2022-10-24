package my.blog.comments.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.domain.Board;
import my.blog.comments.dto.CommentRequest;
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

    private Comments(String content, Board board, User user) {
        this.content = content;
        this.board = board;
        this.user = user;
    }

    private Comments() {

    }

    //==생성 메서드==//
    public static Comments of(Board board, User user, String content) {
        Comments comments = new Comments(content, board, user);
        comments.setUserAndBoard(user, board);

        return comments;
    }

    //==연관관계 편의 메서드==//
    private void setUserAndBoard(User user, Board board) {
        this.user = user;
        this.board = board;
        board.getCommentsList().add(this);
    }

    //==업데이트 메서드==//
    public void editComment(String content) {
        this.content = content;
    }
}
