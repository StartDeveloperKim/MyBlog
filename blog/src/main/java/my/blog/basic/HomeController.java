package my.blog.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.service.BoardLookupService;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.service.CategoryService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import my.blog.web.layout.LayoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BoardLookupService boardLookupService;
    private final LayoutService layoutService;

    @GetMapping("/")
    public String homeForm(@LoginUser SessionUser user, Model model) {
        layoutService.getLayoutInfo(model);
        model.addAttribute("boardList", boardLookupService.getBoardListRecent());

        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        }

        return "index";
    }
}
