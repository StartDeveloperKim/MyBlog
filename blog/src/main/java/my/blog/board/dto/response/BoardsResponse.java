package my.blog.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardsResponse {

    private List<BoardResponse> boards;
    private Paging pageInfo;
}
