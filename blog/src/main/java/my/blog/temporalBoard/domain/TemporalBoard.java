package my.blog.temporalBoard.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.temporalBoard.dto.TemporalBoardReq;

import javax.persistence.*;

@Getter
@SequenceGenerator(
        name = "TEMPORALBOARD_SEQ_GENERATOR",
        sequenceName = "TEMPORALBOARD_SEQ",
        initialValue = 1,
        allocationSize = 1)
@Entity
public class TemporalBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEMPORALBOARD_SEQ_GENERATOR")
    @Column(name = "temporal_board_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String thumbnail;

    private String tags; // 임시저장 이기에 JSON으로 넘어온 태그 String을 그대로 저장한다.

    private Long categoryId;

    protected TemporalBoard(String title, String content, String thumbnail, String tags, Long categoryId) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.tags = tags;
        this.categoryId = categoryId;
    }

    protected TemporalBoard() {
    }

    public static TemporalBoard of(TemporalBoardReq temporalBoardReq) {
        return new TemporalBoard(temporalBoardReq.getTitle(), temporalBoardReq.getContent(),
                temporalBoardReq.getThumbnail(), temporalBoardReq.getTags(),
                temporalBoardReq.getCategoryId());
    }

    public void update(TemporalBoardReq temporalBoardReq) {
        this.title = temporalBoardReq.getTitle();
        this.content = temporalBoardReq.getContent();
        this.thumbnail = temporalBoardReq.getThumbnail();
        this.tags = temporalBoardReq.getTags();
        this.categoryId = temporalBoardReq.getCategoryId();
    }
}
