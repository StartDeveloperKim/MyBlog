package my.blog.boardTag.service;

import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.tag.dto.TagCountResponse;
import my.blog.tag.dto.TagResponse;

import java.util.List;

public interface BoardTagService {

    List<BoardResponse> getTagBoardList(int page, int size, String tagName);
    List<TagResponse> getTagList(Long boardId);

    List<TagCountResponse> getPopularityTag();
    void saveBoardTags(List<String> tags, Long boardId);

    void editBoardTags(List<String> tags, Long boardId);
    Long getCountBoardByTagName(String tagName);
}
