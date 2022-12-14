package my.blog.comments.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor
public class CommentRequest {
    
    @NotBlank(message = "댓글을 입력해주세요")
    @Length(min = 1, max = 100)
    private String comment;

    private Long parentId;
}
