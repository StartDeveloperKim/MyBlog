package my.blog.board.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardRegister {

    private Long userId;
    private String title;
    private String content;
    private String category;
    private String thumbnail;
    private String tags;
}
