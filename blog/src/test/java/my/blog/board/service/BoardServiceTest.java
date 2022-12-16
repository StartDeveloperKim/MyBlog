package my.blog.board.service;

import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static my.blog.factory.EntityFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private BoardServiceImpl boardService;

    private final Long USER_ID = 1L;
    private final Long CATEGORY_ID = 1L;

    private User user;
    private Category category;

    @BeforeEach
    void setUpStubbing() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(newUserInstance()));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(newCategoryInstance(null)));
    }
    @Test
    void 태그가_있을_경우_boardTagRepository_saveAll_호출된다() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(newUserInstance()));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(newCategoryInstance(null)));
        //when
        boardService.writeBoard(any(BoardRegister.class), anyLong());
        //then
        verify(boardRepository.save(any()), times(1));
    }
}
