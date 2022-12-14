package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.tag.domain.TagRepository;
import my.blog.testFactory.EntityFactory;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static my.blog.testFactory.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;
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
    void setUp() {
        user = newUserInstance();
        category = newCategoryInstance(null);
    }

    @Test
    void 태그가_있을_경우_boardTagRepository_saveAll_호출된다() {
//        //given
//        when(userRepository.findById(eq(USER_ID))).thenReturn(Optional.of(newUserInstance()));
//        when(categoryRepository.findById(eq(CATEGORY_ID))).thenReturn(Optional.of(newCategoryInstance(null)));
//        when(boardRepository.save(any()))
//                .thenReturn(newBoardInstance(any(), any()));
//        Mockito.
//        //when
//        boardService.writeBoard(new BoardRegister(), USER_ID);
//        //then
//        verify(boardService.writeBoard(new BoardRegister(), any()), times(1));
    }
}
