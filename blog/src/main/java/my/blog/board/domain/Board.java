package my.blog.board.domain;

import lombok.Builder;
import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.dto.request.BoardRegister;
import my.blog.boardTag.domain.BoardTag;
import my.blog.category.domain.Category;
import my.blog.comments.domain.Comments;
import my.blog.heart.domain.Heart;
import my.blog.user.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comments> commentsList = new ArrayList<>();

    @org.hibernate.annotations.BatchSize(size = 100)
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<BoardTag> boardTags = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Heart> hearts = new ArrayList<>();

    protected Board() {}

    private String getThumbnailURL(String thumbnail) {
        // 사용자가 썸네일을 설정했다면 해당 썸네일 URL로 그렇지 않다면 기본 썸네일 링크를 설정하자.
        String defaultURL = "https://dinfree.com/assets/img/13-title.png";
        if (thumbnail == null || thumbnail.equals("")) {
            return defaultURL;
        }

        return thumbnail;
    }

    public static Board newInstance(User user, Category category, String title, String content, String thumbnail) {
        Board board = new Board();
        board.title = title;
        board.content = content;
        board.thumbnail = board.getThumbnailURL(thumbnail);
        board.hit = 0L;
        board.setUserAndCategory(user, category);

        return board;
    }

    //==연관관계 편의 메서드==//
    private void setUserAndCategory(User user, Category category) {
        this.user = user;
        this.category = category;
        //user.getBoards().add(this);
    }

    public void edit(String title, String content, String thumbnail, Category category) {
        this.title = title;
        this.content = content;
        if (thumbnail != null) {
            this.thumbnail = thumbnail;
        }
        this.category = category;
    }

    public void addHit() {
        this.hit++;
    }
}
