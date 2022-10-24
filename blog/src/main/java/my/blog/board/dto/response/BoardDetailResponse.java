package my.blog.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.blog.board.domain.Board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long boardId;
    private String title;
    private String content;
    private String createDate;
    private String thumbnail;
    //댓글 및 태그 리스트도 필요한데 이는 프론트엔드를 완성하면 해보자


    public BoardDetailResponse(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ISO_DATE);
        this.thumbnail = board.getThumbnail();
    }
}
