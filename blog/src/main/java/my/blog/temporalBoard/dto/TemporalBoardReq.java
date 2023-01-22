package my.blog.temporalBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class TemporalBoardReq {

    private String title;
    private String content;
    private String thumbnail;
    private List<String> tags;
    private Long categoryId;

    public TemporalBoardReq(String title, String content, String thumbnail, List<String> tags, Long categoryId) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.tags = tags;
        this.categoryId = categoryId;
    }
}
