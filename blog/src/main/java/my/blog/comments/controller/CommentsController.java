package my.blog.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.comments.dto.request.CommentRequest;
import my.blog.comments.service.CommentsService;
import my.blog.user.dto.RecognizeUser;
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

    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable("boardId") Long boardId,
                                                           @LoginUser RecognizeUser user) {
        String email = user != null ? user.getEmail() : null;
        Map<String, Object> commentLayoutResult = getCommentLayout(boardId, email);
        return ResponseEntity.ok().body(commentLayoutResult);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                                           @PathVariable("boardId") Long boardId,
                                                           @LoginUser RecognizeUser user) {
        try {
            log.info("comment Info {}", commentRequest.toString());
            commentsService.saveComment(commentRequest, boardId, user.getEmail());

            Map<String, Object> commentLayoutResult = getCommentLayout(boardId, user.getEmail());
            return ResponseEntity.ok().body(commentLayoutResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("commentId") Long commentId,
                                                             @PathVariable("boardId") Long boardId,
                                                             @LoginUser RecognizeUser user) {
        try {
            log.info("삭제댓글 정보 {}", commentId);
            commentsService.removeComment(commentId, boardId);

            Map<String, Object> commentLayoutResult = getCommentLayout(boardId, user.getEmail());
            return ResponseEntity.ok().body(commentLayoutResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private Map<String, Object> getCommentLayout(Long boardId, String email) {
        Map<String, Object> layoutResponse = new HashMap<>();
        layoutResponse.put("comments", commentsService.getComments(boardId, email));
        layoutResponse.put("total", commentsService.getTotalComment(boardId));

        return layoutResponse;
    }
}
