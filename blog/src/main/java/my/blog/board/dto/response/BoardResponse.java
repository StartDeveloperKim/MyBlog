package my.blog.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import my.blog.board.domain.Board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@ToString
public class BoardResponse {

    private Long id;
    private String title;
    private String thumbnail;
    private String createDate;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.thumbnail = board.getThumbnail();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ISO_DATE);
    }

}
