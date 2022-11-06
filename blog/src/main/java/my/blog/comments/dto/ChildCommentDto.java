package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildCommentDto {

    private Long commentId;
    private String content;
    private String createDate;
    private String userName;
    private String userThumbnail;

    public ChildCommentDto(Comments comments) {
        this.commentId = comments.getId();
        this.content = comments.getContent();
        this.createDate = comments.getCreateDate().format(DateTimeFormatter.ISO_DATE);
        this.userName = comments.getUser().getName();
        this.userThumbnail = comments.getUser().getPicture();
    }
}
