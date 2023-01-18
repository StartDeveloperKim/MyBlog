package my.blog.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.service.CategoryService;
import my.blog.user.dto.SessionUser;
import my.blog.user.service.LoginUser;
import my.blog.web.layout.LayoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("userInfo", user.getName());
        }

        return "loginForm";
    }
}
