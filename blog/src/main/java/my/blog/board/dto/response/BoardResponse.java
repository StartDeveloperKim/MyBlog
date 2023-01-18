package my.blog.board.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import my.blog.board.domain.Board;
import my.blog.tag.dto.TagResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long id;
    private String title;
    private String thumbnail;
    private String createDate;
    private Long hit;
//    private List<TagResponse> tags;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.thumbnail = board.getThumbnail();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ISO_DATE);
        this.hit = board.getHit();
//        this.tags = board.getBoardTags().stream()
//                .map(b->new TagResponse(b.getTag().getTagName()))
//                .collect(Collectors.toList());
    }

}
