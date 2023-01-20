package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static my.blog.factory.EntityFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    private final Long ADMIN_ID = 1L;
    private final Long GUEST_ID = 2L;
    private final Long CATEGORY_ID = 1L;
    private final Long BOARD_ID = 1L;

    private final String ADMIN_EMAIL = "ADMIN_EAMIL";
    private final String GUEST_EMAIL = "GUEST_EMAIL";

    @DisplayName("ADMIN 관리자는 게시글을 등록할 수 있다.")
    @Test
    void write_Board_RoleADMIN() {
        //given
        User user = newUserInstance(ADMIN_ID, Role.ADMIN);
        Category category = newCategoryInstance("카테고리", CATEGORY_ID, null);
        Board board = newBoardInstance(user, category, BOARD_ID);

        when(userRepository.findById(ADMIN_ID)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(boardRepository.save(any())).thenReturn(board);

        //when
        Long boardId = boardService.writeBoard(new BoardRegister("테스트", "테스트글", CATEGORY_ID, null, null), ADMIN_EMAIL);

        //then
        assertThat(boardId).isNotNull();
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @DisplayName("GUEST가 글을 작성하려고 시도하면 RuntimeException")
    @Test
    void write_BoardByGuest_throw_RuntimeException() {
        //given
        User user = newUserInstance(GUEST_ID, Role.GUEST);
        when(userRepository.findById(GUEST_ID)).thenReturn(Optional.of(user));

        //when, then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> {
                    boardService.writeBoard(new BoardRegister("테스트", "테스트글", CATEGORY_ID, null, null), GUEST_EMAIL);
                });
        assertEquals("GUEST는 글을 작성할 수 없습니다.", exception.getMessage());
    }
}
