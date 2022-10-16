package my.blog.category.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @Test
    void 중복_카테고리_등록_테스트() {
        //given
        categoryService.saveCategory("스프링");

        //when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> categoryService.saveCategory("스프링"));

        //then
        assertEquals("이미 존재하는 카테고리 입니다.", thrown.getMessage());
    }
}