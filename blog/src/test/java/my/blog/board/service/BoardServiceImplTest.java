package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.response.BoardResponse;
import my.blog.category.service.CategoryService;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

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
    @Autowired
    BoardRepository boardRepository;

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

    @Test
    void 태그_포함해서_게시판_저장테스트() {
//        BoardRegister boardRegister = new BoardRegister(1L, "태그포함 저장테스트", "테스트 성공기원", "스프링", "null", "");

    }
    
    @Test
    void 카테고리별_게시글_조회_테스트() {
        int pageSize=6;
        List<Board> result1 = boardRepository.findByOrderByIdDesc(PageRequest.of(0, pageSize)).getContent();
        List<Board> result2 = boardRepository.findByOrderByIdDesc(PageRequest.of(1, pageSize)).getContent();
        List<Board> result3 = boardRepository.findByOrderByIdDesc(PageRequest.of(2, pageSize)).getContent();

        for (Board board : result1) {
            System.out.println("board.getTitle() + board.getId() = " + board.getTitle() + board.getId());
        }
        for (Board board : result2) {
            System.out.println("board.getTitle() + board.getId() = " + board.getTitle() + board.getId());
        }
        for (Board board : result3) {
            System.out.println("board.getTitle() + board.getId() = " + board.getTitle() + board.getId());
        }
    }
}