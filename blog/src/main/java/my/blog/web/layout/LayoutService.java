package my.blog.web.layout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.service.BoardLookupService;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class LayoutService {

    private final CategoryService categoryService;
    private final BoardLookupService boardLookupService;

    public void getLayoutInfo(Model model) {
        Map<Long, CategoryLayoutDto> categoryList = categoryService.getCategoryList();
        Long boardCount = boardLookupService.getBoardCount();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardCount", boardCount);
    }

    public void getCategoryList(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryList());
    }

}
