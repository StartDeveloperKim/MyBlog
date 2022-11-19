package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.FakeCustomOAuth2UserService;
import my.blog.user.domain.User;
import my.blog.user.dto.OAuthRequest;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardLookupServiceImplTest {

    private FakeCustomOAuth2UserService customOAuth2UserService;

    @Mock
    BoardRepository boardRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    BoardTagRepository boardTagRepository;
    @InjectMocks
    BoardLookupServiceImpl boardLookupService;

    private Category parentCategory;
    private Category childCategory;

    private Board parentBoard;
    private Board childBoard;
    private List<Board> boards;

    private User user;

    @BeforeEach
    void setUp() {
        customOAuth2UserService = new FakeCustomOAuth2UserService();
        user = customOAuth2UserService.loadUser();
        parentCategory = Category.from("스프링", null);
        childCategory = Category.from("MVC", 1L);

        boards = new ArrayList<>();
        parentBoard = Board.of(user, parentCategory, new BoardRegister("테스트1", "테스트입니다1", 1L, null, null));
        childBoard = Board.of(user, parentCategory, new BoardRegister("테스트2", "테스트입니다2", 2L, null, null));
        boards.add(childBoard);
        boards.add(parentBoard);
    }

    @Test
    void 게시글을_조회하는데_없다면_예외를_발생() {
        //given
        given(boardRepository.findByOrderByIdDesc(any())).willReturn(null);
        //when
        assertThrows(EntityNotFoundException.class, ()
                -> boardLookupService.getBoardList(1, 6, "total", null, "0"));
        //then
        verify(boardRepository, times(1)).findByOrderByIdDesc(any());
    }

    @Test
    void step_0을_조회하면_전체게시글_조회() {
        //given
        given(boardRepository.findByOrderByIdDesc(any(Pageable.class))).willReturn(null);
        //when
        assertThrows(EntityNotFoundException.class, ()
                -> boardLookupService.getBoardList(1, 6, "total", null, "0"));
        //then
        verify(boardRepository, times(1)).findByOrderByIdDesc(any());

    }
}
