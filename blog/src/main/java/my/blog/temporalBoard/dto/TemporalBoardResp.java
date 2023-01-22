package my.blog.temporalBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class TemporalBoardResp{

    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private Long categoryId;
    private String createDate;
    private List<String> tags;


}
