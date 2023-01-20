package my.blog.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.request.CategoryRemoveDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.dto.response.CategoryResponseDto;
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
    public CategoryResponseDto categoryAdd(@LoginUser RecognizeUser user,
                                           @RequestBody CategoryAddDto categoryAddDto) {
        log.info("CategoryAddDto {}", categoryAddDto.toString());

        try {
            Long categoryId = categoryService.saveCategory(categoryAddDto.toEntity());
            return new CategoryResponseDto(categoryId, "success");
        } catch (DuplicateCategoryException e) {
            log.info("Category Duplicate Exception : {}", e.getMessage());
            return new CategoryResponseDto(null, "duplicate");
        }

    }

    @DeleteMapping
    public String categoryRemove(@LoginUser RecognizeUser user,
                                 @RequestBody CategoryRemoveDto removeDto) {
        try {
            log.info("삭제요청 {}", removeDto.getCategoryId());

            categoryService.deleteCategory(removeDto.getCategoryId());
            return "success";
        } catch (WritingExistException e) {
            log.error("WritingExistException {}", e.getMessage());
            return "fail";
        }


    }
}
