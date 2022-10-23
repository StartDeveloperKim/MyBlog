package my.blog.board.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.service.BoardService;
import my.blog.category.service.CategoryService;
import my.blog.tag.service.TagService;
import my.blog.tag.tool.ParsingTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static my.blog.board.dto.response.BoardResponse.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @GetMapping
    public String boardListForm(Model model) {
        List<BoardResponse> boards = boardService.getBoardList();
        model.addAttribute("boards", boards);

        return "board/boardListForm";
    }

    @GetMapping("/{id}")
    public String boardDetailForm(@PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoard(id);
        BoardResponse boardResponse = new BoardResponse(board);

        model.addAttribute("board", boardResponse);

        return "board/boardDetailForm";
    }

    @GetMapping("/edit")
    public String boardEditForm(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryList());

        return "board/boardEditForm";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> boardSave(@RequestBody BoardRegister boardRegister) {
        // 비동기로 통신하기 때문에 이에대한 Validation을 만들고 공부하자.
        log.info("Get Data : {}", boardRegister.toString());

        List<String> tags = tagService.saveTags(boardRegister.getTags());

        Long boardId = boardService.writeBoard(boardRegister, tags);

        return ResponseEntity.ok().body(boardId);
    }

    @GetMapping("/edit/{id}")
    public String boardDetailEditForm(@PathVariable("id") Long id,
                                      Model model) {
        Board board = boardService.getBoard(id);
        BoardResponse boardResponse = new BoardResponse(board);

        model.addAttribute("board", boardResponse);

        return "board/boardEditForm";
    }

    @PutMapping("/edit")
    @ResponseBody
    public ResponseEntity<String> boardUpdate(@ModelAttribute BoardUpdate boardUpdate) {
        try {
            boardService.editBoard(boardUpdate);
            return ResponseEntity.ok().body("success");
        } catch (EntityNotFoundException e) {
            log.info("/board/edit error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);

        return ResponseEntity.ok().body("success");
    }
}
