package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.dto.response.BoardUpdateResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BoardLookupService {

    Board getBoard(Long boardId);

    List<BoardResponse> getBoardList(int page, int size, String parentCategory, String childCategory, String step);

    List<BoardResponse> getBoardListRecent();

    Long getBoardCount();

    BoardUpdateResponse getBoardUpdateDto(Long boardId);

    Long getBoardCountByCategory(String parentCategoryName, String childCategoryName);

    List<BoardResponse> getBoardSearchResult(String word, PageRequest request);

    Long getSearchBoardCount(String searchWord);
}
