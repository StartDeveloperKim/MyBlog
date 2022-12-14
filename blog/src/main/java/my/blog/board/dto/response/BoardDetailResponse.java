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
    private Long userId;
    private String title;
    private String content;
    private String createDate;
    private String thumbnail;


    public BoardDetailResponse(Board board) {
        this.boardId = board.getId();
        this.userId = board.getUser().getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ISO_DATE);
        this.thumbnail = board.getThumbnail();
    }
}
