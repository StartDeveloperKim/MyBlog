package my.blog.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import my.blog.board.domain.Board;
import my.blog.tag.dto.TagResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
public class BoardResponse {

    private Long id;
    private String title;
    private String thumbnail;
    private String createDate;
    private Long hit;
    private List<TagResponse> tags;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.thumbnail = board.getThumbnail();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ISO_DATE);
        this.hit = board.getHit();
        this.tags = board.getBoardTags().stream().map(boardTag -> new TagResponse(boardTag.getTag().getTagName())).collect(Collectors.toList());
    }

}
