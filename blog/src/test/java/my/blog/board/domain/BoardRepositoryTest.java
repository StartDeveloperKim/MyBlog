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

    }
}