package my.blog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.service.BoardService;
import my.blog.category.dto.CategoryDto;
import my.blog.category.service.CategoryService;
import my.blog.user.dto.SessionUser;
import my.blog.user.service.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String homeForm(@LoginUser SessionUser user, Model model) {
        List<BoardResponse> boardList = boardService.getBoardListRecent();
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        Long boardCount = boardService.getBoardCount();

        model.addAttribute("boardList", boardList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardCount", boardCount);

        if (user != null) {
            model.addAttribute("userInfo", user.getName());
        }

        return "index";
    }
}
