package my.blog.category.service;

import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.CategoryDto;
import my.blog.category.dto.CategoryRespInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 중복_카테고리_등록_테스트() {
        //given
        categoryService.saveCategory("스프링");

        //when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> categoryService.saveCategory("스프링"));

        //then
        assertEquals("이미 존재하는 카테고리 입니다.", thrown.getMessage());
    }
    
    @Test
    void 카테고리이름_및_카테고리당_글_개수_가져오기() {
        List<CategoryRespInterface> categoryDto = categoryRepository.findCategoryDto();
        for (CategoryRespInterface categoryRespInterface : categoryDto) {
            System.out.println("categoryRespInterface.getName() = " + categoryRespInterface.getName());
            System.out.println("categoryRespInterface.getCategoryNum() = " + categoryRespInterface.getCategoryNum());
        }
    }
}