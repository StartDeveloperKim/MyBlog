package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static my.blog.board.dto.response.BoardResponse.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

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
    public String boardEditForm() {
        return "board/boardEditForm";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> boardSave(@RequestBody BoardRegister boardRegister) {
        // 비동기로 통신하기 때문에 이에대한 Validation을 만들고 공부하자.
        Long boardId = boardService.writeBoard(boardRegister);

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

    @PutMapping("/edit/{id}")
    @ResponseBody
    public String boardUpdate() {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String boardDelete() {
        return null;
    }
}
