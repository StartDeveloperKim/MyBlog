package my.blog.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import my.blog.category.service.CategoryService;
import my.blog.user.dto.RecognizeUser;
import my.blog.user.service.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<Long, CategoryLayoutDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PostMapping
    public ResponseEntity<?> categoryAdd(@LoginUser RecognizeUser user,
                                           @RequestBody CategoryAddDto categoryAddDto) {
        try {
            categoryService.saveCategory(categoryAddDto.toEntity());
            return ResponseEntity.ok(categoryService.getCategoryList());
        } catch (DuplicateCategoryException e) {
            log.error("Category Duplicate Exception : {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> categoryRemove(@LoginUser RecognizeUser user,
                                 @PathVariable("categoryId") Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(categoryService.getCategoryList());
        } catch (WritingExistException e) {
            log.error("WritingExistException {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
