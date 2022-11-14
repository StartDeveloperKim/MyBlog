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
import my.blog.category.service.CategoryService;
import my.blog.comments.dto.CommentResponse;
import my.blog.comments.service.CommentsService;
import my.blog.tag.dto.TagResponse;
import my.blog.tag.service.TagService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import my.blog.web.layout.LayoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value={"/category/{parentCategoryName}/{page}",
            "/category/{parentCategoryName}/{childCategoryName}/{page}"})
    public String boardListForm(@PathVariable(value = "parentCategoryName") String  parentCategoryName,
                                @PathVariable(value = "childCategoryName", required = false) String childCategoryName,
                                @PathVariable(value = "page") int page,
                                @RequestParam(value = "step", required = false, defaultValue = "0") String step,
                                @LoginUser SessionUser user,
                                Model model) {
        childCategoryName = childCategoryName == null ? "" : childCategoryName;

        Paging pagingInfo = Paging.of(page, boardService.getBoardCountByCategory(parentCategoryName, childCategoryName));
        log.info("페이징정보 {}", pagingInfo.toString());

        List<BoardResponse> boards = boardService.getBoardList(page, pagingSize, parentCategoryName, childCategoryName, step);

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

        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        } else {
            model.addAttribute("userInfo", new UserInfo(null, null));
        }
        List<TagResponse> tagList = boardTagService.getTagList(id);
        Map<Long, CommentResponse> comments = commentsService.getComments(id);
        boardService.addHit(id);

        //log.info("tagList {}", tagList);

        model.addAttribute("board", boardResponse);
        model.addAttribute("commentList", comments);
        model.addAttribute("tagList", tagList);

        layoutService.getLayoutInfo(model);

        return "board/boardDetailForm";
    }

    @GetMapping("/edit")
    public String boardEditForm(Model model) {
        layoutService.getCategoryList(model);// EditForm에 카테고리의 개수가 필요할까? 리팩토링 필요코드

        return "board/boardEditForm";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> boardSave(@LoginUser SessionUser user,
                                          @Valid @RequestBody BoardRegister boardRegister) {
        // 비동기로 통신하기 때문에 이에대한 Validation을 만들고 공부하자.
        log.info("Get Data : {}", boardRegister.toString());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // null은 나중에 생각해보고 수정하자
        } // 근데 생각해보면 Spring Security 설정에서 이미 이 곳 접근을 막고있다. 따라서 user가 null일 경우 400을 return할 이유가 없다.
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
    public ResponseEntity<String> boardUpdate(@RequestBody BoardUpdate boardUpdate) {
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
