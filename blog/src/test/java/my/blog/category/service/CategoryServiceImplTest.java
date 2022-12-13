package my.blog.category.service;

import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.dto.response.CategoryInfoDto;
import my.blog.category.dto.response.CategoryLayoutDto;
import my.blog.category.dto.HierarchicalCategory;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import my.blog.category.stub.CategoryRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {


    CategoryRepositoryStub categoryRepositoryStub;//Stub Repository
    @Mock
    BoardRepository boardRepository;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryRepositoryStub = new CategoryRepositoryStub();
    }

    @Test
    void 카테고리_삭제시_아래에_글이_하나이상_존재한다면_카테고리_삭제못함() {
        //given
        given(boardRepository.getBoardCountByCategory(1L)).willReturn(3L);

        //when, then
        assertThrows(WritingExistException.class,
                () -> categoryService.deleteCategory(1L));
    }

    @Test
    void 계층형_카테고리_테스트() {
        //given
        Category parentCategory = Category.newInstance("부모카테고리", null);
        Long parentCategoryId = categoryRepositoryStub.saveParentCategory(parentCategory);

        Category childCategory = Category.newInstance("자식카레고리", parentCategoryId);
        Long childCategoryId = categoryRepositoryStub.saveChildCategory(childCategory);

        List<CategoryInfoDto> response = new ArrayList<>();
        response.add(new CategoryInfoDto(parentCategoryId, null,
                parentCategory.getCategoryName(), 0L));
        response.add(new CategoryInfoDto(childCategoryId, parentCategoryId,
                childCategory.getCategoryName(), 0L));

        //when
        Map<Long, CategoryLayoutDto> result = HierarchicalCategory.from(response);

        //then
        CategoryLayoutDto categoryLayoutDto = result.get(parentCategoryId);
        assertEquals(parentCategoryId, categoryLayoutDto.getId());
        assertEquals(parentCategory.getCategoryName(), categoryLayoutDto.getName());

        List<CategoryLayoutDto> childCategory1 = categoryLayoutDto.getChildCategory();
        CategoryLayoutDto categoryLayoutDto1 = childCategory1.get(0);
        assertEquals(childCategoryId, categoryLayoutDto1.getId());
        assertEquals(childCategory.getCategoryName(), categoryLayoutDto1.getName());
    }

    @Test
    void 중복되는_부모카테고리이름을_등록하려한다면_예외발생() {
        //given
        given(categoryRepository.existsByCategoryName(anyString()))
                .willReturn(true);
        //when, then
        assertThrows(DuplicateCategoryException.class, () ->
                categoryService.saveCategory(new CategoryAddDto("테스트1", null)));
    }

    @Test
    void 중복되는_자식카테고리이름을_등록하려한다면_예외발생() {
        //given
        given(categoryRepository.existByNameAndParentId(anyLong(), anyString()))
                .willReturn(true);
        //when, then
        assertThrows(DuplicateCategoryException.class, () ->
                categoryService.saveCategory(new CategoryAddDto("테스트2", 1L)));
    }
}