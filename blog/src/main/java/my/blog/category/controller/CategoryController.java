package my.blog.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.dto.request.CategoryRemoveDto;
import my.blog.category.dto.response.CategoryResponseDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import my.blog.category.service.CategoryService;
import my.blog.user.dto.SessionUser;
import my.blog.user.dto.UserInfo;
import my.blog.user.service.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String categoryEditForm(@LoginUser SessionUser user, Model model) {
        Map<Long, CategoryLayoutDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        if (user != null) {
            model.addAttribute("userInfo", new UserInfo(user.getUserId(), user.getName()));
        }

        return "category/categoryEditForm";
    }

    @PostMapping("/add")
    @ResponseBody
    public CategoryResponseDto categoryAdd(@RequestBody CategoryAddDto categoryAddDto) {
        log.info("CategoryAddDto {}", categoryAddDto.toString());

        try {
            Long categoryId = categoryService.saveCategory(categoryAddDto);
            return new CategoryResponseDto(categoryId, "success");
        } catch (DuplicateCategoryException e) {
            log.info("Category Duplicate Exception : {}", e.getMessage());
            return new CategoryResponseDto(null, "duplicate");
        }

    }

    @PostMapping("/remove")
    @ResponseBody
    public String categoryRemove(@RequestBody CategoryRemoveDto removeDto) {
        try {
            log.info("삭제요청 {}", removeDto.getCategoryId());

            categoryService.deleteCategory(removeDto.getCategoryId());
            return "success";
        } catch (WritingExistException e) {
            log.info("This Category has writing : {}", e.getMessage());
            return "fail";
        }


    }
}
