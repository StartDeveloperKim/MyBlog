package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.service.BoardService;
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
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Long> boardSave(@LoginUser SessionUser user,
                                          @Valid @RequestBody BoardRegister boardRegister) {

        log.info("Get Data : {}", boardRegister.toString());
        Long boardId;

        if (boardRegister.getTags().equals("")) {
            boardId = boardService.writeBoard(boardRegister, user.getUserId());
        } else {
            List<String> tags = tagService.saveTags(boardRegister.getTags());
            boardId = boardService.writeBoardWithTag(boardRegister, tags, user.getUserId());
        }

        return ResponseEntity.ok().body(boardId);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> boardUpdate(@PathVariable("id") Long boardId,
                                              @RequestBody BoardUpdate boardUpdate) {
        log.info("edit Board {}, {}", boardId, boardUpdate.toString());
        try {
            List<String> tags = tagService.saveTags(boardUpdate.getTags());
            boardService.editBoard(boardUpdate, boardId, tags);
            return ResponseEntity.ok().body("success");
        } catch (EntityNotFoundException e) {
            log.info("/board/edit error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().body("success");
    }
}
