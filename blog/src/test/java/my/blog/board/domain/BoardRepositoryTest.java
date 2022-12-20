package my.blog.board.domain;

import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.factory.EntityFactory;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.dto.OAuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static my.blog.factory.EntityFactory.*;
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
        user = userRepository.save(newUserInstance(1L, Role.ADMIN));
        category = categoryRepository.save(newCategoryInstance("카테고리", 1L, null));
    }

    @Test
    void 최신_6개의_글을_불러온다() {
        //given
        Board savedBoard1 = boardRepository.save(newBoardInstance(user, category, null));
        Board savedBoard2 = boardRepository.save(newBoardInstance(user, category, null));
        Board savedBoard3 = boardRepository.save(newBoardInstance(user, category, null));
        Board savedBoard4 = boardRepository.save(newBoardInstance(user, category, null));
        Board savedBoard5 = boardRepository.save(newBoardInstance(user, category, null));
        Board savedBoard6 = boardRepository.save(newBoardInstance(user, category, null));

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
        boardRepository.save(newBoardInstance(user, category, null));
        //when
        Long count = boardRepository.getBoardCountByCategoryId(category.getId());
        //then
        assertThat(count).isEqualTo(1);
    }
}
