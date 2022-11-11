package my.blog.category.domain;

import my.blog.category.dto.CategoryInfoDto;
import my.blog.category.dto.CategoryRespInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 카테고리_저장_테스트() {
        //given
        Category category = Category.from("테스트", null);
        //when
        Category saveCategory = categoryRepository.save(category);
        //then
        assertEquals("테스트", saveCategory.getCategoryName());
    }

    @Test
    void 카테고리이름으로_찾기_테스트() {
        //given
        Category category = Category.from("테스트", null);
        categoryRepository.save(category);
        //when
        Category findCategory = categoryRepository.findByCategoryName("테스트");
        //then
        assertEquals("테스트", findCategory.getCategoryName());
    }

    @Test
    void 카테고리_레이아웃_정보_가져오기_테스트() {
        //given
        Category category1 = Category.from("테스트1", null);
        Category category2 = Category.from("테스트2", null);
        Category savedCategory1 = categoryRepository.save(category1);
        Category savedCategory2 = categoryRepository.save(category2);
        //when
        List<CategoryInfoDto> categoryDto = categoryRepository.findCategoryDto();
        //then
        CategoryInfoDto respInterface1 = categoryDto.get(0);
        assertEquals(savedCategory1.getId(), respInterface1.getId());
        assertEquals(savedCategory1.getCategoryName(), respInterface1.getName());

        CategoryInfoDto respInterface2 = categoryDto.get(1);
        assertEquals(savedCategory2.getId(), respInterface2.getId());
        assertEquals(savedCategory2.getCategoryName(), respInterface2.getName());
    }

    @Test
    void 카테고리정보_가져오기_테스트() {
        //given
        Category parentCategory = Category.from("테스트1", null);
        Category savedParentCategory = categoryRepository.save(parentCategory);

        Category childCategory = Category.from("테스트2", savedParentCategory.getId());
        Category savedChildCategory = categoryRepository.save(childCategory);
        //when
        List<CategoryInfoDto> categoryInfoDtos = categoryRepository.findCategoryDto();

        //then
        CategoryInfoDto categoryInfoDto1 = categoryInfoDtos.get(0);
        assertEquals(savedParentCategory.getId(), categoryInfoDto1.getId());
        assertEquals(savedParentCategory.getCategoryName(), categoryInfoDto1.getName());
        assertNull(categoryInfoDto1.getCategoryNum());
        assertNull(categoryInfoDto1.getParentCategoryId());

        CategoryInfoDto categoryInfoDto2 = categoryInfoDtos.get(1);
        assertEquals(savedChildCategory.getId(), categoryInfoDto2.getId());
        assertEquals(savedChildCategory.getCategoryName(), categoryInfoDto2.getName());
        assertEquals(savedChildCategory.getParentCategoryId(), categoryInfoDto2.getParentCategoryId());
        assertNull(categoryInfoDto2.getCategoryNum());

    }

}