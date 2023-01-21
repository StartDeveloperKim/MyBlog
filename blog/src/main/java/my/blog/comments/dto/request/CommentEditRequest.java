package my.blog.comments.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentEditRequest {

    private Long commentId;
    private String comment;
}
