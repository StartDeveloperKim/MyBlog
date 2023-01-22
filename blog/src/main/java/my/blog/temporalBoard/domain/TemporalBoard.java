package my.blog.temporalBoard.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.temporalBoard.dto.TemporalBoardReq;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class TemporalBoard extends BaseTimeEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "temporal_board_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String thumbnail;

    private String tags;

    private Long categoryId;

    protected TemporalBoard() {
    }

    public static TemporalBoard newInstance(String title, String content, String thumbnail, List<String> tags, Long categoryId) {
        TemporalBoard temporalBoard = new TemporalBoard();
        temporalBoard.title = title;
        temporalBoard.content = content;
        temporalBoard.thumbnail = thumbnail;
        temporalBoard.tags = tags.toString();
        temporalBoard.categoryId = categoryId;
        return temporalBoard;
    }

    public void update(TemporalBoardReq temporalBoardReq) {
        this.title = temporalBoardReq.getTitle();
        this.content = temporalBoardReq.getContent();
        this.thumbnail = temporalBoardReq.getThumbnail();
        this.tags = temporalBoardReq.getTags().toString();
        this.categoryId = temporalBoardReq.getCategoryId();
    }
}
