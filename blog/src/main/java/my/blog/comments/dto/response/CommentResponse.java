package my.blog.comments.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import my.blog.comments.domain.Comments;
import my.blog.comments.dto.response.ChildCommentDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long parentId;
    private Long userId;
    private String userName;
    private String userThumbnail;
    private String content;
    private String createDate;
    private final List<ChildCommentDto> childCommentDtos = new ArrayList<>();

    public CommentResponse(Comments comments) {
        this.userId = comments.getUser().getId();
        this.commentId = comments.getId();
        this.userName = comments.getUser().getName();
        this.userThumbnail = comments.getUser().getPicture();
        this.parentId = comments.getId();
        this.content = comments.getContent();
        this.createDate = comments.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
