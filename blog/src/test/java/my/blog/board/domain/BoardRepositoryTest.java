package my.blog.board.domain;

import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.FakeCustomOAuth2UserService;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.dto.OAuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private User savedUser;
    private Category savedParentCategory;
    private Category savedChildCategory;

    @BeforeEach // 더미데이터 삽입
    void setUp() {
        FakeCustomOAuth2UserService fakeUserService = new FakeCustomOAuth2UserService();
        user = fakeUserService.loadUser();
        savedUser = userRepository.save(user);
        //savedUser = userRepository.save(user);
        savedParentCategory = categoryRepository.save(Category.from("테스트", null));
        savedChildCategory = categoryRepository.save(Category.from("자식카테고리", savedParentCategory.getId()));
    }

    @Test
    void 부모카테고리를_조회하면_자식_카테고리까지_포함되서_보여진다() {
        //given
        BoardRegister boardRegister1 = new BoardRegister("테스트1", "테스트1입니다.", savedParentCategory.getCategoryName(), null, null);
        Board board1 = Board.of(savedUser, savedParentCategory, boardRegister1);
        Board savedBoard1 = boardRepository.save(board1);

        BoardRegister boardRegister2 = new BoardRegister("테스트2", "테스트2입니다.", savedChildCategory.getCategoryName(), null, null);
        Board board2 = Board.of(savedUser, savedChildCategory, boardRegister2);
        Board savedBoard2 = boardRepository.save(board2);

        PageRequest pageRequest = PageRequest.of(0, 6);
        //when
        List<Board> findBoards = boardRepository.findByCategoryId(savedParentCategory.getId(), pageRequest)
                .getContent();

        //then
        assertThat(findBoards.size()).isEqualTo(2);

        assertThat(findBoards.get(0).getId())
                .isEqualTo(savedBoard2.getId());
        assertThat(findBoards.get(1).getId())
                .isEqualTo(savedBoard1.getId());
    }
}