package my.blog.board.repository;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static my.blog.testFactory.EntityFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BoardRepository boardRepository;

    User user;
    Category category;

    @BeforeEach
    void setUp() {
        user = userRepository.save(newUserInstance());
        category = categoryRepository.save(newCategoryInstance(null));
    }

    @Test
    void 최신_6개의_글을_불러온다() {
        //given
        Board savedBoard1 = boardRepository.save(newBoardInstance(user, category));
        Board savedBoard2 = boardRepository.save(newBoardInstance(user, category));
        Board savedBoard3 = boardRepository.save(newBoardInstance(user, category));
        Board savedBoard4 = boardRepository.save(newBoardInstance(user, category));
        Board savedBoard5 = boardRepository.save(newBoardInstance(user, category));
        Board savedBoard6 = boardRepository.save(newBoardInstance(user, category));

        //when
        List<Board> boards = boardRepository.findTop6ByOrderByCreateDateDesc();
        //then
        assertThat(savedBoard1.getId()).isEqualTo(boards.get(5).getId());
        assertThat(savedBoard2.getId()).isEqualTo(boards.get(4).getId());
        assertThat(savedBoard3.getId()).isEqualTo(boards.get(3).getId());
        assertThat(savedBoard4.getId()).isEqualTo(boards.get(2).getId());
        assertThat(savedBoard5.getId()).isEqualTo(boards.get(1).getId());
        assertThat(savedBoard6.getId()).isEqualTo(boards.get(0).getId());
    }

    @Test
    void 카테고리ID로_게시글_개수_조회하기() {
        //given
        boardRepository.save(newBoardInstance(user, category));
        //when
        Long count = boardRepository.getBoardCountByCategoryId(category.getId());
        //then
        assertThat(count).isEqualTo(1);
    }
}
