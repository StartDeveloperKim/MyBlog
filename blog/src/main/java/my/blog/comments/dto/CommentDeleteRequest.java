package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteRequest {

    private Long boardId;
    private Long commentId;
    private boolean parentComment;
}
