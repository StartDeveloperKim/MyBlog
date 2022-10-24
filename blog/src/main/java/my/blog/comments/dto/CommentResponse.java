package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String comment;

    public CommentResponse(Comments comments) {
        this.commentId = comments.getId();
        this.comment = comments.getContent();
    }
}
