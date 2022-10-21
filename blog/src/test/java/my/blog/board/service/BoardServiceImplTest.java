package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.response.BoardResponse;
import my.blog.category.service.CategoryService;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserRepository userRepository;

    @Test
    void 카테고리_없이_글_저장() {
        /*User user = new User("kim", "김민수");
        User saveUser = userRepository.save(user); // 영속화된 User

        BoardRegister boardRegister = new BoardRegister(saveUser.getId(), "테스트 글", "테스트", null, null, null);
        Long boardId = boardService.writeBoard(boardRegister);*/
    }

    @Test
    void 카테고리_지정_후_글_저장() {
        /*User user = new User("kim", "김민수");
        User saveUser = userRepository.save(user); // 영속화된 User

        categoryService.saveCategory("스프링");


        BoardRegister boardRegister = new BoardRegister(saveUser.getId(), "테스트 글", "테스트", "스프링", null, null);
        Long boardId = boardService.writeBoard(boardRegister);

        Board board = boardService.getBoard(boardId);

        assertThat(board.getTitle()).isEqualTo(boardRegister.getTitle());*/
    }

    @Test
    void 최신글_4개_가져오기() {
        List<BoardResponse> boardListRecent = boardService.getBoardListRecent();
        for (BoardResponse boardResponse : boardListRecent) {
            System.out.println("boardResponse.toString() = " + boardResponse.toString());
        }
    }
}