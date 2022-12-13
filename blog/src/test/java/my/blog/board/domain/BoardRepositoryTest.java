package my.blog.board.domain;

import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.FakeCustomOAuth2UserService;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

        savedParentCategory = categoryRepository.save(Category.newInstance("테스트", null));
        savedChildCategory = categoryRepository.save(Category.newInstance("자식카테고리", savedParentCategory.getId()));
    }

    @Test
    void 최신6개의_글이_조회된다() {
        //given
        BoardRegister boardRegister = new BoardRegister("테스트", "테스트글입니다.", savedParentCategory.getId(), "thumbnail", "tags");
        Board board = Board.newInstance(savedUser, savedParentCategory, boardRegister);

        Board saveBoard1 = boardRepository.save(board);
        Board saveBoard2 = boardRepository.save(board);
        Board saveBoard3 = boardRepository.save(board);
        Board saveBoard4 = boardRepository.save(board);
        Board saveBoard5 = boardRepository.save(board);
        Board saveBoard6 = boardRepository.save(board);

        //when
        List<Board> findBoards = boardRepository.findTop6ByOrderByCreateDateDesc();

        //then
        checkEqualBoard(saveBoard6, findBoards.get(0));
        checkEqualBoard(saveBoard5, findBoards.get(0));
        checkEqualBoard(saveBoard4, findBoards.get(0));
        checkEqualBoard(saveBoard3, findBoards.get(0));
        checkEqualBoard(saveBoard2, findBoards.get(0));
        checkEqualBoard(saveBoard1, findBoards.get(0));
    }

    private void checkEqualBoard(Board saveBoard, Board findBoard) {
        assertThat(saveBoard.getId()).isEqualTo(findBoard.getId());
    }
}