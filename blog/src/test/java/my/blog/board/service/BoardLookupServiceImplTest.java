package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.factory.EntityFactory;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.dto.OAuthRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static my.blog.factory.EntityFactory.*;
import static my.blog.factory.EntityFactory.newBoardInstance;
import static my.blog.factory.EntityFactory.newCategoryInstance;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardLookupServiceImplTest {


    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BoardTagRepository boardTagRepository;

    @InjectMocks
    private BoardLookupServiceImpl boardLookupService;

    private static final Long BOARD_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long CATEGORY_ID = 1L;
    private static final Long CHILD_CATEGORY_ID = 2L;

    private User user;
    private Category category;
    @BeforeEach
    void setUp() {
        user = newUserInstance(USER_ID, Role.GUEST);
        category = newCategoryInstance("카테고리", 1L, null);
    }


    @DisplayName("잘못된 boardId로 요청하면 EntityNotFoundException 발생")
    @Test
    void wrong_boardId_Request_EntityNotFoundException() {
        //given
        when(boardRepository.findById(BOARD_ID)).thenReturn(Optional.empty()); // 비어있는 Optional

        //when, then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> {
                    boardLookupService.getBoard(BOARD_ID);
                });
        assertEquals("게시물이 없습니다.", exception.getMessage());
    }

    @DisplayName("카테고리 step이 0인 경우 전체글 조회")
    @Test
    void select_boards_when_step0() {
        //given
        Board board = Board.newInstance(user, category, "테스트1", "테스트글1", null);
        ReflectionTestUtils.setField(board, "createDate", LocalDateTime.now());
        List<Board> boards = List.of(board);

        when(boardRepository.findBoardsOrderByIdDesc(any(Pageable.class)))
                .thenReturn(boards);
        //when
        boardLookupService.getBoardList(1, 6, "Total", "", "0");

        //then
        verify(boardRepository, times(1)).findBoardsOrderByIdDesc(any(Pageable.class));
    }

    @DisplayName("카테고리 step이 1인 경우 부모카테고리와 자식카테고리 글 모두 조회")
    @Test
    void select_boards_when_step1() {
        //given
        Board board1 = newBoardInstance(user, category, BOARD_ID);
        List<Board> boards = List.of(board1);
        Category category = newCategoryInstance("parent", CATEGORY_ID, null);

        when(categoryRepository.findByNameAndParentIdIsNull("부모카테고리"))
                .thenReturn(category);
        when(boardRepository.findByCategoryId(anyLong(), any(Pageable.class)))
                .thenReturn(boards);
        //when
        boardLookupService.getBoardList(1, 6, "부모카테고리", null, "1");
        //then
        assertAll(
                () -> verify(categoryRepository, times(1)).findByNameAndParentIdIsNull("부모카테고리"),
                () -> verify(boardRepository, times(1)).findByCategoryId(anyLong(), any(Pageable.class))
        );
    }

    @DisplayName("카테고리 step이 2인 경우 자식카테고리 글만 조회")
    @Test
    void select_boards_when_step2() {
        //given
        Board board1 = newBoardInstance(user, category, BOARD_ID);
        List<Board> boards = List.of(board1);
        Category category = newCategoryInstance("child", CHILD_CATEGORY_ID, CATEGORY_ID);

        when(categoryRepository.findByNameAndParentName("부모카테고리", "자식카테고리"))
                .thenReturn(category);
        when(boardRepository.findByCategoryId(anyLong(), any(Pageable.class)))
                .thenReturn(boards);
        //when
        boardLookupService.getBoardList(1, 6, "부모카테고리", "자식카테고리", "2");
        //then
        assertAll(
                () -> verify(categoryRepository, times(1)).findByNameAndParentName("부모카테고리", "자식카테고리"),
                () -> verify(boardRepository, times(1)).findByCategoryId(anyLong(), any(Pageable.class))
        );
    }

    @DisplayName("step이 0, 1, 2가 아니라면 빈 Array 반환")
    @Test
    void step_is_not_123_return_emptyArray() {
        //given
        //when
        List<BoardResponse> result = boardLookupService.getBoardList(1, 6, "부모카테고리", "자식카테고리", "4");
        //then
        Assertions.assertThat(result.size()).isEqualTo(0);
    }

    @DisplayName("count 요청 카테고리 이름이 total이라면 전체 글 개수 조회")
    @Test
    void request_category_for_total_getTotalCount() {
        //given
        when(boardRepository.count()).thenReturn(10L);
        //when
        boardLookupService.getBoardCountByCategory("total", null);
        //then
        verify(boardRepository, times(1)).count();
    }

    @DisplayName("검색어가 null 또는 비어있다면 IllegalArgumentException")
    @Test
    void void_search_word_IllegalArgumentException() {
        //given
        PageRequest pageRequest = PageRequest.of(1, 6);
        //then, when
        IllegalArgumentException voidException = assertThrows(IllegalArgumentException.class,
                () -> boardLookupService.getBoardSearchResult("", pageRequest));
        IllegalArgumentException nullException = assertThrows(IllegalArgumentException.class,
                () -> boardLookupService.getBoardSearchResult(null, pageRequest));
        assertEquals("검색어가 비어있습니다.", voidException.getMessage());
        assertEquals("검색어가 비어있습니다.", nullException.getMessage());
    }
}