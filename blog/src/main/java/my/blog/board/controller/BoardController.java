package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.service.BoardService;
import my.blog.boardTag.service.BoardTagService;
import my.blog.tag.service.TagService;
import my.blog.user.dto.SessionUser;
import my.blog.user.service.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardTagService boardTagService;
    private final TagService tagService;

    // 어쨌든 게시글을 저장하기 위한 엔티티 들이다(Tag, BoardTag)
    // 그런데 BoardService 메서드로 묶어버리기에는 복잡성이 올라간다.
    // 하지만 따로 처리하자니 트랜잭션이 각각 처리된다.(하나 작업 실패했을 때 그 위에서 했던 작업이 rollback 되지 않음)
    // 음... 하나의 클래스로 묶자니 그냥 계층+1 느낌이다.
    // 해답이 뭘까???
    @PostMapping
    public ResponseEntity<Long> boardSave(@LoginUser SessionUser user,
                                          @Valid @RequestBody BoardRegister boardRegister) {

        Long boardId = boardService.writeBoard(boardRegister, user.getUserId());
        List<String> tags = tagService.saveTags(boardRegister.getTags());
        if (tags.size() != 0) {
            boardTagService.saveBoardTags(tags, boardId);
        }
        return ResponseEntity.ok().body(boardId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> boardUpdate(@PathVariable("id") Long boardId,
                                              @RequestBody BoardUpdate boardUpdate) {
        try {
            boardService.editBoard(boardUpdate, boardId);
            List<String> tags = tagService.saveTags(boardUpdate.getTags());
            if (tags.size() != 0) {
                boardTagService.editBoardTags(tags, boardId);
            }
            return ResponseEntity.ok().body("success");
        } catch (EntityNotFoundException e) {
            log.info("/board/edit error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().body("success");
    }
}
