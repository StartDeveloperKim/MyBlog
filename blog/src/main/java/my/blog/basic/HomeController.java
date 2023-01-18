package my.blog.basic;

import lombok.RequiredArgsConstructor;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.service.BoardLookupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomeController {

    private final BoardLookupService boardLookupService;

    @GetMapping("/")
    public List<BoardResponse> homeForm() {
        return boardLookupService.getBoardListRecent();
    }
}
