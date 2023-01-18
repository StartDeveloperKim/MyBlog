package my.blog.board.dto.request;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BoardUpdate {

    private String title;
    private String content;
    private String thumbnail;
    private Long categoryId;
    private List<String> tags;
}
