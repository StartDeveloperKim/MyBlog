package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.response.BoardResponse;
import my.blog.boardTag.domain.BoardTag;
import my.blog.category.service.CategoryService;
import my.blog.comments.domain.Comments;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    EntityManager em;

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
    void 최신글_6개_가져오기() {
        List<BoardResponse> boardListRecent = boardService.getBoardListRecent();
        assertEquals(boardListRecent.size(), 6);
    }

    @Test
    void 카테고리별_게시글_조회_테스트() {
        int pageSize = 6;
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

    @Test
    void 태그리스트_가져오기_테스트() {
        List<Board> findBoards = em.createQuery("select b from Board b join fetch b.boardTags", Board.class)
                .getResultList();

        for (Board findBoard : findBoards) {
            System.out.println("=====" + findBoard.getTitle() + "====");
            for (BoardTag boardTag : findBoard.getBoardTags()) {
                System.out.println("boardTag.toString() = " + boardTag.toString());
            }
        }

        org.junit.jupiter.api.Assertions.assertEquals(7, findBoards.size());
    }
}