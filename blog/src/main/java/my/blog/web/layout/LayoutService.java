package my.blog.web.layout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.board.service.BoardLookupService;
import my.blog.boardTag.service.BoardTagService;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.service.CategoryService;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.service.TagService;
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
    private final BoardTagService boardTagService;

    public void getLayoutInfo(Model model) {
        model.addAttribute("sideBarTagList", boardTagService.getPopularityTag());
        model.addAttribute("categoryList", categoryService.getCategoryList());
        model.addAttribute("boardCount", boardLookupService.getBoardCount());
    }

    public void getCategoryList(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryList());
    }

}
