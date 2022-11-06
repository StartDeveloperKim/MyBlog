package my.blog.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    
    @NotBlank(message = "댓글을 입력해주세요")
    private String comment;
    private Long parentId;
}
