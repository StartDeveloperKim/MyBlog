package my.blog.comments.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CommentDeleteRequest {

    private Long boardId;
    private Long commentId;
    private boolean parentComment;
}
