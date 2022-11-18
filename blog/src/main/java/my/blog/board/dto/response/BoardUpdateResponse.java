package my.blog.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import my.blog.board.domain.Board;
import my.blog.boardTag.domain.BoardTag;
import my.blog.tag.domain.Tag;
import my.blog.tag.dto.TagResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class BoardUpdateResponse {

    private Long boardId;
    private String title;
    private String content;
    private Long categoryId;
    private List<String> tags;
    private String thumbnail;


    public BoardUpdateResponse(Board board, List<BoardTag> boardTags) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.categoryId = board.getCategory().getId(); // 지연로딩 발생
        this.tags = parsingTagName(boardTags);
        this.thumbnail = board.getThumbnail();
    }

    private List<String> parsingTagName(List<BoardTag> boardTags) {
        return boardTags.stream()
                .map(boardTag -> boardTag.getTag().getTagName())
                .collect(Collectors.toList());
    }
}
