package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String comment;
    private String createDate;

    public CommentResponse(Comments comments) {
        this.commentId = comments.getId();
        this.comment = comments.getContent();
        this.createDate = comments.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
