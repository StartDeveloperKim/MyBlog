package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.dto.response.BoardDetailResponse;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.BoardUpdateResponse;
import my.blog.board.dto.response.Paging;
import my.blog.board.service.BoardLookupService;
import my.blog.board.service.BoardService;
import my.blog.boardTag.service.BoardTagService;
import my.blog.comments.dto.response.CommentResponse;
import my.blog.comments.service.CommentsService;
import my.blog.heart.service.HeartService;
import my.blog.tag.dto.TagResponse;
import my.blog.temporalBoard.dto.TemporalBoardResp;
import my.blog.temporalBoard.service.TemporalBoardService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import my.blog.web.layout.LayoutService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardFormController {

    private final BoardService boardService;
    private final BoardLookupService boardLookupService;
    private final TemporalBoardService temporalBoardService;

    private final CommentsService commentsService;
    private final BoardTagService boardTagService;
    private final HeartService heartService;
    private final LayoutService layoutService;

    private final int PAGING_SIZE = 6;

    @GetMapping(value={"/category/{parentCategoryName}/{page}", "/category/{parentCategoryName}/{childCategoryName}/{page}"})
    public String boardListForm(@PathVariable(value = "parentCategoryName") String  parentCategoryName,
                                @PathVariable(value = "childCategoryName", required = false) String childCategoryName,
                                @PathVariable(value = "page") int page,
                                @RequestParam(value = "step", required = false, defaultValue = "0") String step,
                                @LoginUser SessionUser user,
                                Model model) {
        Paging pagingInfo = Paging.of(page, boardLookupService.getBoardCountByCategory(parentCategoryName, childCategoryName));
        List<BoardResponse> boards = boardLookupService.getBoardList(page, PAGING_SIZE, parentCategoryName, childCategoryName, step);

        model.addAttribute("boardList", boards);
        model.addAttribute("pagingInfo", pagingInfo);

        layoutService.getLayoutInfo(model);
        userInfoSaveInModel(user, model);

        return "board/boardListForm";
    }

    @GetMapping("/search")
    public String boardSearch(@RequestParam("query") String query,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              Model model) {

        log.info("searchInfo {}", query);
        // ??????????????? ????????? ???????????? -> count ??????????????? ??? ?????? ?????? ??????
        Paging pagingInfo = Paging.of(page, boardLookupService.getSearchBoardCount(query));
        List<BoardResponse> boardSearchResult = boardLookupService.getBoardSearchResult(query, PageRequest.of(page-1, PAGING_SIZE));
        model.addAttribute("boardList", boardSearchResult);
        model.addAttribute("pagingInfo", pagingInfo);

        layoutService.getLayoutInfo(model);
        
        return "board/boardListForm";
    }

    @GetMapping("/{id}")
    public String boardDetailForm(@LoginUser SessionUser user, @PathVariable("id") Long id, Model model) {
        Board board = boardLookupService.getBoard(id);
        BoardDetailResponse boardResponse = BoardDetailResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .thumbnail(board.getThumbnail())
                .createDate(board.getCreateDate().format(DateTimeFormatter.ISO_DATE))
                .build();

        List<TagResponse> tagList = boardTagService.getTagList(id);
        Map<Long, CommentResponse> comments = commentsService.getComments(id);
        boardService.addHit(id);

        if (user != null) {
            model.addAttribute("boardLike", heartService.isUserLikeBoard(id, user.getUserId()));
        } else {
            model.addAttribute("boardLike", false);
        }

        userInfoSaveInModel(user, model);

        model.addAttribute("board", boardResponse);
        model.addAttribute("commentList", comments);
        model.addAttribute("tagList", tagList);

        layoutService.getLayoutInfo(model);

        return "board/boardDetailForm";
    }

    @GetMapping("/edit")
    public String boardEditForm(Model model) {
        layoutService.getCategoryList(model);// EditForm??? ??????????????? ????????? ????????????? ???????????? ????????????
        TemporalBoardResp recentTemporalBoard = temporalBoardService.getRecentTemporalBoard();

        if (recentTemporalBoard != null) {
            model.addAttribute("temporalBoardFlag", true);
            model.addAttribute("temporalBoard", recentTemporalBoard);
        }

        return "board/boardEditForm";
    }

    @GetMapping("/edit/{id}")
    public String boardDetailEditForm(@PathVariable("id") Long id, Model model) {
        BoardUpdateResponse boardUpdateRes = boardLookupService.getBoardUpdateDto(id);
        log.info("BoardUpdateDto {}", boardUpdateRes.toString());

        model.addAttribute("board", boardUpdateRes);
        model.addAttribute("updateFlag", true);
        layoutService.getCategoryList(model);

        return "board/boardEditForm";
    }

    private static void userInfoSaveInModel(SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        } else {
            model.addAttribute("userInfo", new UserInfo(null, null));
        }
    }
}
