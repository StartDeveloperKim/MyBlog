package my.blog.heart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.heart.dto.HeartRequest;
import my.blog.heart.dto.HeartResponse;
import my.blog.heart.service.HeartService;
import my.blog.user.dto.RecognizeUser;
import my.blog.user.service.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    @GetMapping("/{boardId}")
    public ResponseEntity<HeartResponse> getHeart(@PathVariable("boardId") Long boardId,
                                                       @LoginUser RecognizeUser user) {
        HeartResponse heartResponse =
                new HeartResponse(heartService.isUserHaveHeart(user.getEmail(), boardId));
        return ResponseEntity.ok().body(heartResponse);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<String> addHeart(@LoginUser RecognizeUser user,
                                           @PathVariable("boardId") Long boardId) {
        try {
            log.info("좋아요 요청: user {}, boardId {}", user.toString(), boardId);
            heartService.saveHeart(user.getEmail(), boardId);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            log.error("addHeart Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> removeHeart(@LoginUser RecognizeUser user,
                                              @PathVariable("boardId") Long boardId) {
        try {
            log.info("좋아요 삭제 요청: user {}, boardId {}", user.toString(), boardId);
            heartService.deleteHeart(user.getEmail(), boardId);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            log.error("removeHeart Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }
}
