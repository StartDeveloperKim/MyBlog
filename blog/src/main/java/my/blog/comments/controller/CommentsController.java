package my.blog.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.comments.domain.CommentsRepository;
import my.blog.comments.dto.CommentDeleteRequest;
import my.blog.comments.dto.CommentRequest;
import my.blog.comments.dto.CommentResponse;
import my.blog.comments.service.CommentsService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
@RestController
public class CommentsController {

    private final CommentsService commentsService;

    /*댓글 레이아웃에 필요한 정보들이 반복되고 있다 이를 리팩토링하자.*/
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getComments(@LoginUser SessionUser user, @PathVariable("boardId") Long boardId) {
        Map<String, Object> map = new HashMap<>();

        Map<Long, CommentResponse> comments = commentsService.getComments(boardId);
        int totalComment = commentsService.getTotalComment(boardId);

        map.put("comments", comments);
        map.put("total", totalComment);

        log.info("getComments {}", map);

        return ResponseEntity.ok().body(map);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                                           @PathVariable("id") Long boardId,
                                                           @LoginUser SessionUser user) {
        Map<String, Object> map = new HashMap<>();

        try {
            log.info("comment Info {}", commentRequest.toString());

            commentsService.saveComment(commentRequest, boardId, user.getUserId());
            Map<Long, CommentResponse> comments = commentsService.getComments(boardId);
            int totalComment = commentsService.getTotalComment(boardId);

            map.put("comments", comments);
            map.put("total", totalComment);
            map.put("userInfo", new UserInfo(user.getUserId(), user.getName()));

            return ResponseEntity.ok().body(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody CommentDeleteRequest commentDeleteRequest,
                                                             @LoginUser SessionUser user) {
        Map<String, Object> map = new HashMap<>();
        log.info("삭제댓글 정보 {}", commentDeleteRequest.toString());
        try {
            commentsService.removeComment(commentDeleteRequest);

            Map<Long, CommentResponse> comments = commentsService.getComments(commentDeleteRequest.getBoardId());
            int totalComment = commentsService.getTotalComment(commentDeleteRequest.getBoardId());

            map.put("comments", comments);
            map.put("total", totalComment);
            map.put("userInfo", new UserInfo(user.getUserId(), user.getName()));

            return ResponseEntity.ok().body(map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
