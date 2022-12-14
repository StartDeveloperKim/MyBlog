package my.blog.board.dto.response;

import lombok.*;
import my.blog.board.domain.Board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long boardId;
    private String title;
    private String content;
    private String createDate;
    private String thumbnail;

    @Builder
    public BoardDetailResponse(Long boardId, String title, String content, String createDate, String thumbnail) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.thumbnail = thumbnail;
    }
}
