package my.blog.board.domain;

import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomBoardRepositoryImplTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 제목에_포함된_단어로_검색하기() {
        //given
        User user = userRepository.findById(1L).orElseThrow();
        Category category = categoryRepository.findById(2L).orElseThrow();
        Board board = Board.of(user, category, new BoardRegister("테스트김안녕하세요", "테스트입니다.", 2L, null, ""));
        Board savedBoard = boardRepository.save(board);

        //when
        PageRequest pageRequest = PageRequest.of(0, 6);
        List<Board> boards = boardRepository.searchBoardByTitle("트김", pageRequest);
        //then
        Board board1 = boards.get(0);
        assertThat(savedBoard.getId()).isEqualTo(board1.getId());
        assertThat(savedBoard.getTitle()).isEqualTo(board1.getTitle());
    }

}