package my.blog.board.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardRegister {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @NotBlank(message = "카테고리를 입력해주세요")
    private String category;

    private String thumbnail;

    @NotBlank(message = "태그를 하나이상 입력해주세요")
    private String tags;
}
