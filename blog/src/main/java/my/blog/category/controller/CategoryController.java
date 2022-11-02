package my.blog.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.dto.CategoryDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import my.blog.category.service.CategoryService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String categoryEditForm(@LoginUser SessionUser user, Model model) {
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        }

        return "category/categoryEditForm";
    }

    @PostMapping("/add")
    @ResponseBody
    public String categoryAdd(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.saveCategory(categoryDto.getName());
            return "success";
        } catch (DuplicateCategoryException e) {
            log.info("Category Duplicate Exception : {}", e.getMessage());
            return "duplicate";
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public String categoryRemove(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.deleteCategory(categoryDto.getName());
            return "success";
        } catch (WritingExistException e) {
            log.info("This Category has writing : {}", e.getMessage());
            return "fail";
        }


    }
}
