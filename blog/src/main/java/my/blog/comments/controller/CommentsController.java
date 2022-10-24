package my.blog.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.comments.domain.CommentsRepository;
import my.blog.comments.dto.CommentRequest;
import my.blog.comments.dto.CommentResponse;
import my.blog.comments.service.CommentsService;
import my.blog.user.dto.SessionUser;
import my.blog.user.service.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
@RestController
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable("boardId") Long boardId) {
        Map<String, Object> map = new HashMap<>();
        List<CommentResponse> comments = commentsService.getComments(boardId);

        map.put("comments", comments);

        return ResponseEntity.ok().body(map);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> saveComment(@RequestBody CommentRequest commentRequest,
                                              @PathVariable("id") Long boardId,
                                              @LoginUser SessionUser user) {
        try {
            log.info("comment:{}, boardId:{}", commentRequest.getComment(), boardId);

            commentsService.saveComment(commentRequest.getComment(), boardId, user.getUserId());
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }
}
