package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private String userName;
    private String userThumbnail;
    private Long commentId;
    private String content;
    private String createDate;

    public CommentResponse(Comments comments) {
        this.userName = comments.getUser().getName();
        this.userThumbnail = comments.getUser().getPicture();
        this.commentId = comments.getId();
        this.content = comments.getContent();
        this.createDate = comments.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
