package my.blog.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardUpdate {

    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private String category;
    private String tags;
}
