package my.blog.web.layout;

import lombok.extern.slf4j.Slf4j;
import my.blog.board.service.BoardService;
import my.blog.category.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@Service
public class LayoutService {

    private CategoryService categoryService;
    private BoardService boardService;

}
