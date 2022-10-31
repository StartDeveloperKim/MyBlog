package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardDetailResponse;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.Paging;
import my.blog.board.service.BoardService;
import my.blog.boardTag.service.BoardTagService;
import my.blog.category.dto.CategoryDto;
import my.blog.category.service.CategoryService;
import my.blog.comments.dto.CommentResponse;
import my.blog.comments.service.CommentsService;
import my.blog.tag.service.TagService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import my.blog.web.layout.LayoutService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final TagService tagService;
    private final CommentsService commentsService;
    private final BoardTagService boardTagService;
    private final LayoutService layoutService;

    private final int pagingSize = 6;

    @GetMapping
    public String boardListForm(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                @RequestParam(value = "category", required = false) String category,
                                @RequestParam(value = "step", required = false, defaultValue = "0") String step,
                                @LoginUser SessionUser user,
                                Model model) {
        Paging pagingInfo;
        if (category.equals("total")) {
            pagingInfo = Paging.of(page, boardService.getBoardCount());
        } else {
            pagingInfo = Paging.of(page, boardService.getBoardCountByCategory(category));
        }

        log.info("Paging Infomation = {}", pagingInfo.toString());

        List<BoardResponse> boards = boardService.getBoardList(page, pagingSize, category, step);

        model.addAttribute("boardList", boards);
        model.addAttribute("pagingInfo", pagingInfo);

        layoutService.getLayoutInfo(model);

        /*로그인유저 인증코드가 중복되고 있다. 어떻게 멤버가 로그인되었다는 것을 확인해서 랜더링을 해야하지??*/
        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        }

        return "board/boardListForm";
    }

    @GetMapping("/{id}")
    public String boardDetailForm(@LoginUser SessionUser user, @PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoard(id);
        BoardDetailResponse boardResponse = new BoardDetailResponse(board);

        List<String> tagList = boardTagService.getTagList(id);
        log.info("tagList {}", tagList);

        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        }

        List<CommentResponse> comments = commentsService.getComments(id);

        model.addAttribute("board", boardResponse);
        model.addAttribute("commentList", comments);
        model.addAttribute("tagList", tagList);

        layoutService.getLayoutInfo(model);

        return "board/boardDetailForm";
    }

    @GetMapping("/edit/{id}")
    public String boardEditForm(@PathVariable("id") Long boardId, Model model) {
        Board board = boardService.getBoard(boardId);
        BoardResponse boardResponse = new BoardResponse(board);

        model.addAttribute("boardResponse", boardResponse);
        model.addAttribute("editFlag", true); // 작성

        return "board/boardEditForm";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> boardSave(@LoginUser SessionUser user,
                                          @RequestBody BoardRegister boardRegister) {
        // 비동기로 통신하기 때문에 이에대한 Validation을 만들고 공부하자.
        log.info("Get Data : {}", boardRegister.toString());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // null은 나중에 생각해보고 수정하자
        }
        Long boardId;

        if (boardRegister.getTags().equals("")) {
            boardId = boardService.writeBoard(boardRegister, user.getUserId());
        } else {
            List<String> tags = tagService.saveTags(boardRegister.getTags());
            boardId = boardService.writeBoardWithTag(boardRegister, tags, user.getUserId());
        }

        return ResponseEntity.ok().body(boardId);
    }

    @GetMapping("/edit/{id}")
    public String boardDetailEditForm(@PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoard(id);
        BoardResponse boardResponse = new BoardResponse(board);

        model.addAttribute("board", boardResponse);


        return "board/boardEditForm";
    }

    @PostMapping("/edit")
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

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);

        return ResponseEntity.ok().body("success");
    }
}
