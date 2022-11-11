package my.blog.board.service;

import my.blog.board.domain.Board;
import my.blog.board.dto.request.BoardRegister;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.repository.MemoryBoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.tag.domain.TagRepository;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.dto.OAuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    private MemoryBoardRepository boardRepository;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        // 어차피 board 엔티티를 만들 때 필요한건 카테고리ID와 유저ID인데...
        given(categoryRepository.findByCategoryName("스프링"))
                .willReturn(Category.from("스프링", null));
    }

}