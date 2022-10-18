package my.blog.heart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.heart.dto.HeartRequest;
import my.blog.heart.dto.HeartResponse;
import my.blog.heart.service.HeartService;
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
    public ResponseEntity<HeartResponse> getHeartCount(@PathVariable("boardId") Long boardId) {
        Long heartCount = heartService.getHeartCount(boardId);
        HeartResponse heartResponse = new HeartResponse(heartCount);

        return ResponseEntity.ok().body(heartResponse);
    }

    @PostMapping
    public ResponseEntity<String> addHeart(@RequestBody HeartRequest heartRequest) {
        try {
            heartService.saveHeart(heartRequest.getUserId(), heartRequest.getBoardId());
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            log.info("addHeart Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> removeHeart(@RequestBody HeartRequest heartRequest) {
        try {
            heartService.deleteHeart(heartRequest.getUserId(), heartRequest.getBoardId());
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            log.info("removeHeart Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }
}
