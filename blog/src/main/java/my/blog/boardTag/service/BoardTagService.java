package my.blog.boardTag.service;

import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;

import java.util.List;

public interface BoardTagService {

    List<BoardResponse> getTagBoardList(int page, int size, String tagName);

    List<String> getTagList(Long boardId);
    Long getCountBoardByTagName(String tagName);
}
