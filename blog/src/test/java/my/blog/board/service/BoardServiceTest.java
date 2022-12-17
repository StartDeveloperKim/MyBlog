package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.factory.mockEntity.MockBoard;
import my.blog.factory.mockEntity.MockCategory;
import my.blog.factory.mockEntity.MockUser;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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

    private final Long ADMIN_ID = 1L;
    private final Long GUEST_ID = 2L;
    private final Long CATEGORY_ID = 1L;
    private final Long BOARD_ID = 1L;

    @DisplayName("ADMIN 관리자는 게시글을 등록할 수 있다.")
    @Test
    void write_Board_RoleADMIN() {
        //given
        User user = new MockUser(ADMIN_ID, "김어드민", Role.ADMIN);
        Category category = new MockCategory(CATEGORY_ID, "부모카테고리", null);
        Board board = MockBoard.builder()
                .id(BOARD_ID)
                .title("테스트")
                .content("테스트클").build();

        when(userRepository.findById(ADMIN_ID)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(boardRepository.save(any())).thenReturn(board);

        //when
        Long boardId = boardService.writeBoard(new BoardRegister("테스트", "테스트글", CATEGORY_ID, null, null), ADMIN_ID);

        //then
        assertThat(boardId).isNotNull();
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @DisplayName("GUEST가 글을 작성하려고 시도하면 RuntimeException")
    @Test
    void write_BoardByGuest_throw_RuntimeException() {
        //given
        User user = new MockUser(GUEST_ID, "김게스트", Role.GUEST);
        when(userRepository.findById(GUEST_ID)).thenReturn(Optional.of(user));

        //when, then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> {
                    boardService.writeBoard(new BoardRegister("테스트", "테스트글", CATEGORY_ID, null, null), GUEST_ID);
                });
        assertEquals("GUEST는 글을 작성할 수 없습니다.", exception.getMessage());
    }
}
