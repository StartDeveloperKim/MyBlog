package my.blog.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.dto.CategoryDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import my.blog.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> categoryList() {
        Map<String, Object> response = new HashMap<>();
        List<CategoryDto> categoryList = categoryService.getCategoryList();

        response.put("categoryList", categoryList);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/plus")
    public String categoryAdd(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.saveCategory(categoryDto.getName());
            return "success";
        } catch (DuplicateCategoryException e) {
            log.info("Category Duplicate Exception : {}", e.getMessage());
            return "duplicate";
        }
    }

    @DeleteMapping("/remove")
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
