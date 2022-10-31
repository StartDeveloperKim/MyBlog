package my.blog.web.layout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.service.BoardService;
import my.blog.category.dto.CategoryDto;
import my.blog.category.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class LayoutService {

    private final CategoryService categoryService;
    private final BoardService boardService;

    public void getLayoutInfo(Model model) {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        Long boardCount = boardService.getBoardCount();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardCount", boardCount);
    }

}
