package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.dto.response.*;
import my.blog.board.service.BoardLookupService;
import my.blog.board.service.BoardService;
import my.blog.boardTag.service.BoardTagService;
import my.blog.category.dto.response.CategoryEditDto;
import my.blog.category.service.CategoryService;
import my.blog.temporalBoard.dto.TemporalBoardResp;
import my.blog.temporalBoard.service.TemporalBoardService;
import my.blog.user.domain.Role;
import my.blog.user.dto.RecognizeUser;
import my.blog.user.service.LoginUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/board")
public class BoardFormController {

    private final BoardService boardService;
    private final BoardLookupService boardLookupService;
    private final TemporalBoardService temporalBoardService;
    private final BoardTagService boardTagService;
    private final CategoryService categoryService;

    private final int PAGING_SIZE = 6;

    @GetMapping(value={"/{parentCategoryName}/{page}/{step}", "/{parentCategoryName}/{childCategoryName}/{page}/{step}"})
    public BoardsResponse boardListForm(@PathVariable(value = "parentCategoryName") String  parentCategoryName,
                                        @PathVariable(value = "childCategoryName", required = false) String childCategoryName,
                                        @PathVariable(value = "page") int page,
                                        @PathVariable(value = "step", required = false) String step){
        Paging pagingInfo = Paging.of(page, boardLookupService.getBoardCountByCategory(parentCategoryName, childCategoryName));
        List<BoardResponse> boards = boardLookupService.getBoardList(page, PAGING_SIZE, parentCategoryName, childCategoryName, step);

        return new BoardsResponse(boards, pagingInfo);
    }

    @GetMapping("/search")
    public BoardsResponse boardSearch(@RequestParam("search") String searchMode,
                                      @RequestParam("query") String query,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        if (searchMode.equals("title")){
            Paging pagingInfo = Paging.of(page, boardLookupService.getSearchBoardCount(query));
            List<BoardResponse> boardSearchResult = boardLookupService.getBoardSearchResult(query, PageRequest.of(page-1, PAGING_SIZE));

            return new BoardsResponse(boardSearchResult, pagingInfo);
        } else if (searchMode.equals("tag")) {
            return getBoardsResponse(page, query);
        }
        return null;
    }

    @GetMapping("/tag")
    public BoardsResponse boardListFormByTags(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam("tag") String tag) {
        return getBoardsResponse(page, tag);
    }

    @NotNull
    private BoardsResponse getBoardsResponse(int page, String tag) {
        Paging pagingInfo = Paging.of(page, boardTagService.getCountBoardByTagName(tag));
        List<BoardResponse> tagBoardList = boardTagService.getTagBoardList(page, PAGING_SIZE, tag);

        return new BoardsResponse(tagBoardList, pagingInfo);
    }

    @GetMapping("/{id}")
    public BoardDetailResponse boardDetailForm(@PathVariable("id") Long id,
                                               @LoginUser RecognizeUser user) {
        BoardDetailResponse boardResponse = new BoardDetailResponse(boardLookupService.getBoard(id), user == null ? null : user.getRole());
        boardService.addHit(id);

        return boardResponse;
    }

    @GetMapping("/edit")
    public ResponseEntity<?> boardEditForm(@LoginUser RecognizeUser user) {
        if (user.getRole() == Role.ADMIN) {
            List<CategoryEditDto> allCategory = categoryService.getAllCategory();
            TemporalBoardResp recentTemporalBoard = temporalBoardService.getRecentTemporalBoard();
            return ResponseEntity.ok(new BoardEditResponse<TemporalBoardResp>(allCategory, recentTemporalBoard));
        } else {
            log.error("권한이 {}인 사용자 {}가 editForm에 접근하려 합니다.", user.getRole(), user.getEmail());
            return ResponseEntity.badRequest().body("접근 권한이 없습니다.");
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> boardDetailEditForm(@PathVariable("id") Long id) {
        BoardUpdateResponse boardUpdateRes = boardLookupService.getBoardUpdateDto(id);
        List<CategoryEditDto> allCategory = categoryService.getAllCategory();
        log.info("BoardUpdateDto {}", boardUpdateRes.toString());

        return ResponseEntity.ok(new BoardEditResponse<BoardUpdateResponse>(allCategory, boardUpdateRes));
    }
}
