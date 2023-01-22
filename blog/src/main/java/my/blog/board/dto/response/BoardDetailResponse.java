package my.blog.board.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import my.blog.board.domain.Board;
import my.blog.user.domain.Role;

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

    private boolean isModify;

    public BoardDetailResponse(Board board, Role role) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createDate = board.getCreateDate().format(DateTimeFormatter.ISO_DATE);
        this.thumbnail = board.getThumbnail();
        this.isModify = (role == Role.ADMIN);
    }
}
