package my.blog.category.domain;

import my.blog.category.dto.response.CategoryInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category savedParentCategory;
    private Category savedChildCategory;

    @BeforeEach
    void setUp() {
        Category parentCategory = Category.newInstance("테스트1", null);
        savedParentCategory = categoryRepository.save(parentCategory);

        Category childCategory = Category.newInstance("테스트2", savedParentCategory.getId());
        savedChildCategory = categoryRepository.save(childCategory);
    }

    @Test
    void 카테고리이름으로_찾기_테스트() {
        //when
        Category findCategory = categoryRepository.findByCategoryName("테스트1");
        //then
        assertEquals("테스트1", findCategory.getCategoryName());
    }

    @Test
    void 카테고리정보_가져오기_테스트() {

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

    @Test
    void 카테고리이름과_부모_아이디로_카테고리_존재여부_조회하기() {
        //when
        boolean findCategory = categoryRepository.existByNameAndParentId(savedParentCategory.getId(),
                savedChildCategory.getCategoryName());
        //then
        assertThat(findCategory).isTrue();
    }

    @Test
    void 부모카테고리와_자식카테고리이름으로_카테고리찾기() {
        //when
        Category findCategory = categoryRepository.findByNameAndParentName("테스트1", "테스트2");
        //then
        assertThat(findCategory.getId()).isEqualTo(savedChildCategory.getId());
    }

}