package my.blog.temporalBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor
public class TemporalBoardReq {

    @NotBlank(message = "제목을 입력하셔야 임시저장이 가능합니다.")
    private String title;
    private String content;
    private String thumbnail;
    private String tags;
    private Long categoryId;

    public TemporalBoardReq(String title, String content, String thumbnail, String tags, Long categoryId) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.tags = tags;
        this.categoryId = categoryId;
    }
}
