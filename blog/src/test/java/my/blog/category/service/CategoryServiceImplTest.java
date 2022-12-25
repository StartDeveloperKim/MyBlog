package my.blog.category.service;

import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.category.dto.request.CategoryAddDto;
import my.blog.category.exception.DuplicateCategoryException;
import my.blog.category.exception.WritingExistException;
import my.blog.factory.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static my.blog.factory.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    private final String PARENT_CATEGORY = "부모카테고리";
    private final String CHILD_CATEGORY = "자식카테고리";

    private final Long PARENT_ID = 1L;
    private final Long CHILD_ID = 2L;

    @Test
    void 같은_이름의_카테고리를_저장하려하면_DuplicateCategoryException발생() {
        //given
        when(categoryRepository.existsByCategoryName(anyString())).thenReturn(true);
        //when
        DuplicateCategoryException duplicateCategoryException = assertThrows(DuplicateCategoryException.class,
                () -> categoryService.saveCategory(newCategoryInstance(PARENT_CATEGORY, PARENT_ID, null)));
        //then
        assertEquals("사용중인 카테고리 이름입니다.", duplicateCategoryException.getMessage());
    }

    @Test
    void 같은_이름의_카테고리가_없다면_카테고리를_저장한다() {
        //given
        Category category = newCategoryInstance(PARENT_CATEGORY, PARENT_ID, null);
        when(categoryRepository.existsByCategoryName(anyString())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);
        //when
        Long result = categoryService.saveCategory(category);
        //then
        assertThat(result).isEqualTo(PARENT_ID);
    }

    @Test
    void 카테고리_아래에_글이_존재하면_삭제할_수_없다() {
        //given
        when(boardRepository.getBoardCountByCategory(PARENT_ID)).thenReturn(1L);
        //when
        WritingExistException writingExistException = assertThrows(WritingExistException.class,
                () -> categoryService.deleteCategory(PARENT_ID));
        //then
        assertEquals("카테고리 아래에 글이 존재합니다.", writingExistException.getMessage());
    }

}