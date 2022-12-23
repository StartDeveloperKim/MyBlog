package my.blog.category.domain;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.category.dto.response.CategoryInfoDto;
import my.blog.factory.EntityFactory;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static my.blog.factory.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    private static final String PARENT_CATEGORY = "부모카테고리";
    private static final String CHILD_CATEGORY = "자식카테고리";

    private static final Long parentId = 1L;
    private static final Long childId = 2L;

    private Category parentCategory;
    private Category childCategory;

    private User user;

    @BeforeEach
    void setUp() {
        //given
        user = userRepository.save(newUserInstance(1L, Role.ADMIN));
        parentCategory = categoryRepository.save(newCategoryInstance(PARENT_CATEGORY, null, null));
        childCategory = categoryRepository.save(newCategoryInstance(CHILD_CATEGORY, null, parentCategory.getId()));
    }

    @Test
    void 카테고리이름으로_부모카테고리_하나를_조회한다() {
        //given
        //when
        Category findCategory = categoryRepository.findByNameAndParentIdIsNull(PARENT_CATEGORY);
        //then
        assertThat(parentCategory.getId()).isEqualTo(findCategory.getId());
    }

    @Test
    void 부모카테고리이름과_자식카테고리이름으로_자식카테고리_조회하기() {
        //given
        //when
        Category result = categoryRepository.findByNameAndParentName(PARENT_CATEGORY, CHILD_CATEGORY);
        //then
        assertThat(childCategory.getId()).isEqualTo(result.getId());
        assertThat(childCategory.getCategoryName()).isEqualTo(result.getCategoryName());
    }

    @Test
    void 자식카테고리_중_동일한이름의_카테고리가_이미_존재한다면_True() {
        //given
        //when
        boolean result = categoryRepository.existByNameAndParentId(parentCategory.getId(), CHILD_CATEGORY);
        //then
        assertThat(result).isTrue();
    }

    @Test
    void 사이드바_레이아웃에_필요한_카테고리_정보를_조회한다() {
        //given
        boardRepository.save(newBoardInstance(user, parentCategory, 1L));
        boardRepository.save(newBoardInstance(user, childCategory, 2L));
        //when
        List<CategoryInfoDto> result = categoryRepository.getCategoryInfoDto();
        //then
        CategoryInfoDto categoryInfoDto1 = result.get(0);
        assertAll(
                () -> assertThat(parentCategory.getId()).isEqualTo(categoryInfoDto1.getId()),
                () -> assertThat(parentCategory.getCategoryName()).isEqualTo(categoryInfoDto1.getName()),
                () -> assertThat(categoryInfoDto1.getCategoryNum()).isEqualTo(1)
        );

        CategoryInfoDto categoryInfoDto2 = result.get(1);
        assertAll(
                () -> assertThat(childCategory.getId()).isEqualTo(categoryInfoDto2.getId()),
                () -> assertThat(childCategory.getParentCategoryId()).isEqualTo(categoryInfoDto2.getParentCategoryId()),
                () -> assertThat(childCategory.getCategoryName()).isEqualTo(categoryInfoDto2.getName()),
                () -> assertThat(categoryInfoDto2.getCategoryNum()).isEqualTo(1)
        );


    }
}