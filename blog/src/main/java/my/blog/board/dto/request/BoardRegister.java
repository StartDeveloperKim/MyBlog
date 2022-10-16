package my.blog.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRegister {

    private Long userId;
    private String title;
    private String content;
    private String category;
    private String thumbnail;
    private List<String> tags;
}
