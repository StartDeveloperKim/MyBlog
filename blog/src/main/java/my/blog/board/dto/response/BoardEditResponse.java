package my.blog.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import my.blog.category.dto.response.CategoryEditDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.temporalBoard.dto.TemporalBoardResp;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BoardEditResponse<T> {
    private List<CategoryEditDto> categoryList;
    private T boardResponse;
}
