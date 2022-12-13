package my.blog.comments.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.domain.Board;
import my.blog.user.domain.User;

import javax.persistence.*;

@Getter
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comments_id", nullable = false)
    private Long id;

    private String content;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Comments() {}

    //==생성 메서드==//
    public static Comments newInstance(Board board, User user, String content, Long parentId) {
        Comments comments = new Comments();
        comments.content = content;
        comments.parentId = parentId;
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
