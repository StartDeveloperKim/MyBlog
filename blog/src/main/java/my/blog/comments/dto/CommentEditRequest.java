package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEditRequest {

    private Long commentId;
    private Long boardId;
    private String content;
}
