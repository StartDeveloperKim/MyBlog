package my.blog.board.domain;

import my.blog.board.dto.request.BoardRegister;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.dto.OAuthRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private User savedUser;
    private Category savedCategory;

    @BeforeEach // 더미데이터 삽입
    void setUp() {
        OAuthRequest oAuthRequest = new OAuthRequest();
        User user = oAuthRequest.toEntity();
        savedUser = userRepository.save(user);
        savedCategory = categoryRepository.save(Category.from("테스트", null));
    }

    @Test
    void 부모카테고리를_조회하면_자식_카테고리까지_포함되서_보여진다() {
        //given
        BoardRegister boardRegister1 = new BoardRegister("테스트1", "테스트1입니다.", savedCategory.getCategoryName(), null, null);
        Board board1 = Board.of(savedUser, savedCategory, boardRegister1);
        Board savedBoard1 = boardRepository.save(board1);

        Category childCategory = categoryRepository.save(Category.from("자식카테고리", savedCategory.getId()));
        BoardRegister boardRegister2 = new BoardRegister("테스트2", "테스트2입니다.", childCategory.getCategoryName(), null, null);
        Board board2 = Board.of(savedUser, childCategory, boardRegister2);
        Board savedBoard2 = boardRepository.save(board2);
        //when

        //then
    }
}