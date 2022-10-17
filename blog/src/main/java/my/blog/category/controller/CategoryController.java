package my.blog.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.dto.CategoryDto;
import my.blog.category.service.CategoryService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/edit")
    public String categoryEdit(Model model) {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("categories", categoryList);

        return "category/categoryEditForm";
    }
}
