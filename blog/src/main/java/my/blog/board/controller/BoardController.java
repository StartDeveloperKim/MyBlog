package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.request.BoardUpdate;
import my.blog.board.dto.response.BoardDetailResponse;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.BoardUpdateResponse;
import my.blog.board.dto.response.Paging;
import my.blog.board.service.BoardLookupService;
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
    private final BoardLookupService boardLookupService;

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

        Paging pagingInfo = Paging.of(page, boardLookupService.getBoardCountByCategory(parentCategoryName, childCategoryName));
        List<BoardResponse> boards = boardLookupService.getBoardList(page, pagingSize, parentCategoryName, childCategoryName, step);

        model.addAttribute("boardList", boards);
        model.addAttribute("pagingInfo", pagingInfo);

        layoutService.getLayoutInfo(model);
        userInfoSaveInModel(user, model);

        return "board/boardListForm";
    }

    @GetMapping("/{id}")
    public String boardDetailForm(@LoginUser SessionUser user, @PathVariable("id") Long id, Model model) {
        Board board = boardLookupService.getBoard(id);
        BoardDetailResponse boardResponse = new BoardDetailResponse(board);

        userInfoSaveInModel(user, model);

        List<TagResponse> tagList = boardTagService.getTagList(id);
        Map<Long, CommentResponse> comments = commentsService.getComments(id);
        boardService.addHit(id);


        model.addAttribute("board", boardResponse);
        model.addAttribute("commentList", comments);
        model.addAttribute("tagList", tagList);

        layoutService.getLayoutInfo(model);

        return "board/boardDetailForm";
    }

    private static void userInfoSaveInModel(SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        } else {
            model.addAttribute("userInfo", new UserInfo(null, null));
        }
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

    @GetMapping("/edit/{id}")
    public String boardDetailEditForm(@PathVariable("id") Long id, Model model) {
        BoardUpdateResponse boardUpdateRes = boardLookupService.getBoardUpdateDto(id);
        log.info("BoardUpdateDto {}", boardUpdateRes.toString());

        model.addAttribute("board", boardUpdateRes);
        model.addAttribute("updateFlag", true);
        layoutService.getCategoryList(model);
        // getCategoryList는 카테고리당 글 개수까지 집계해서 보여준다.
        // 하지만 EditForm에서는 단순히 ID와 이름 정도만 필요하다 이 점 수정하자.
        return "board/boardEditForm";
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<String> boardDelete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);

        return ResponseEntity.ok().body("success");
    }
}
