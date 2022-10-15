package my.blog.board.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.category.domain.Category;
import my.blog.comments.domain.Comments;
import my.blog.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "board_id", nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String content;

    @Column(nullable = false)
    private String thumbnail;

    @Column(columnDefinition = "bigint default 0", nullable = false)
    private Long hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "board")
    private List<Comments> commentsList = new ArrayList<>();

    protected Board() {
    }
}
