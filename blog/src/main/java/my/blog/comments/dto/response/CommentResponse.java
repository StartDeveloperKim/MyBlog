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

    // 삭제 가능 여부를 DTO에 넣어보내는 건 어떨까? 고려해보자
    private Long commentId;
    private Long parentId;
    private boolean isRemove;
    private String userName;
    private String userThumbnail;
    private String content;
    private String createDate;
    private final List<ChildCommentDto> childCommentDtos = new ArrayList<>();

    public CommentResponse(Comments comments, String email) {
        this.isRemove = comments.getUser().getEmail().equals(email);
        this.commentId = comments.getId();
        this.userName = comments.getUser().getName();
        this.userThumbnail = comments.getUser().getPicture();
        this.parentId = comments.getId();
        this.content = comments.getContent();
        this.createDate = comments.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
