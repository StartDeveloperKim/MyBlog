package my.blog.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.domain.Board;
import my.blog.board.dto.response.*;
import my.blog.board.service.BoardLookupService;
import my.blog.board.service.BoardService;
import my.blog.category.dto.response.CategoryEditDto;
import my.blog.category.service.CategoryService;
import my.blog.temporalBoard.dto.TemporalBoardResp;
import my.blog.temporalBoard.service.TemporalBoardService;
import my.blog.user.domain.Role;
import my.blog.user.dto.RecognizeUser;
import my.blog.user.service.LoginUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/board")
public class BoardFormController {

    private final BoardService boardService;
    private final BoardLookupService boardLookupService;
    private final TemporalBoardService temporalBoardService;
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
    public BoardsResponse boardSearch(@RequestParam("query") String query,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        Paging pagingInfo = Paging.of(page, boardLookupService.getSearchBoardCount(query));
        List<BoardResponse> boardSearchResult = boardLookupService.getBoardSearchResult(query, PageRequest.of(page-1, PAGING_SIZE));
        
        return new BoardsResponse(boardSearchResult, pagingInfo);
    }

    @GetMapping("/{id}")
    public BoardDetailResponse boardDetailForm(@PathVariable("id") Long id) {
        Board board = boardLookupService.getBoard(id);
        BoardDetailResponse boardResponse = BoardDetailResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .thumbnail(board.getThumbnail())
                .createDate(board.getCreateDate().format(DateTimeFormatter.ISO_DATE))
                .build();
        boardService.addHit(id);

        return boardResponse;
    }

    @GetMapping("/edit")
    public ResponseEntity<?> boardEditForm(@LoginUser RecognizeUser user) {
        if (user.getRole() == Role.ADMIN) {
            List<CategoryEditDto> allCategory = categoryService.getAllCategory();
            TemporalBoardResp recentTemporalBoard = temporalBoardService.getRecentTemporalBoard();
            return ResponseEntity.ok(new BoardEditResponse(allCategory, recentTemporalBoard));
        } else {
            log.error("권한이 {}인 사용자 {}가 editForm에 접근하려 합니다.", user.getRole(), user.getEmail());
            return ResponseEntity.badRequest().body("접근 권한이 없습니다.");
        }
    }

    @GetMapping("/edit/{id}")
    public BoardUpdateResponse boardDetailEditForm(@PathVariable("id") Long id, Model model) {
        BoardUpdateResponse boardUpdateRes = boardLookupService.getBoardUpdateDto(id);
        log.info("BoardUpdateDto {}", boardUpdateRes.toString());

        return boardUpdateRes;
    }
}
