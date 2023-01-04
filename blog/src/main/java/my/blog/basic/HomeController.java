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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HomeController {

    private final BoardLookupService boardLookupService;

    @GetMapping("/")
    public ResponseEntity<List<BoardResponse>> homeForm() {
        return ResponseEntity.ok(boardLookupService.getBoardListRecent());
    }
}
