package my.blog.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long parentId;
    private String userName;
    private String userThumbnail;
    private String content;
    private String createDate;
    private List<ChildCommentDto> childCommentDtos = new ArrayList<>();

    public CommentResponse(Comments comments) {
        this.commentId = comments.getId();
        this.userName = comments.getUser().getName();
        this.userThumbnail = comments.getUser().getPicture();
        this.parentId = comments.getId();
        this.content = comments.getContent();
        this.createDate = comments.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
