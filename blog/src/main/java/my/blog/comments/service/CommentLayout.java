package my.blog.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.comments.dto.response.CommentResponse;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommentLayout {

    private final CommentsService commentsService;

    public Map<String, Object> getCommentLayout(Long boardId, SessionUser user) {
        Map<String, Object> layoutResponse = new HashMap<>();

        Map<Long, CommentResponse> comments = commentsService.getComments(boardId);
        int totalComment = commentsService.getTotalComment(boardId);

        layoutResponse.put("comments", comments);
        layoutResponse.put("total", totalComment);
        layoutResponse.put("userInfo", new UserInfo(user.getUserId(), user.getName()));

        return layoutResponse;
    }
}
