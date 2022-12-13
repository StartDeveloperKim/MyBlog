package my.blog.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.comments.dto.request.CommentDeleteRequest;
import my.blog.comments.dto.request.CommentRequest;
import my.blog.comments.dto.response.CommentResponse;
import my.blog.comments.service.CommentsService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentsController {

    private final CommentsService commentsService;

    /*댓글 레이아웃에 필요한 정보들이 반복되고 있다 이를 리팩토링하자.*/
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getComments(@LoginUser SessionUser user, @PathVariable("boardId") Long boardId) {

        Map<String, Object> commentLayoutResult = getCommentLayout(boardId, user);
        return ResponseEntity.ok().body(commentLayoutResult);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                                           @PathVariable("id") Long boardId,
                                                           @LoginUser SessionUser user) {
        try {
            log.info("comment Info {}", commentRequest.toString());
            commentsService.saveComment(commentRequest, boardId, user.getUserId());

            Map<String, Object> commentLayoutResult = getCommentLayout(boardId, user);
            return ResponseEntity.ok().body(commentLayoutResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest,
                                                             @LoginUser SessionUser user) {
        try {
            log.info("삭제댓글 정보 {}", commentDeleteRequest.toString());
            commentsService.removeComment(commentDeleteRequest);

            Map<String, Object> commentLayoutResult = getCommentLayout(commentDeleteRequest.getBoardId(), user);
            return ResponseEntity.ok().body(commentLayoutResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private Map<String, Object> getCommentLayout(Long boardId, SessionUser user) {
        Map<String, Object> layoutResponse = new HashMap<>();

        Map<Long, CommentResponse> comments = commentsService.getComments(boardId);
        int totalComment = commentsService.getTotalComment(boardId);

        layoutResponse.put("comments", comments);
        layoutResponse.put("total", totalComment);
        layoutResponse.put("userInfo", new UserInfo(user.getUserId(), user.getName()));

        return layoutResponse;
    }
}
