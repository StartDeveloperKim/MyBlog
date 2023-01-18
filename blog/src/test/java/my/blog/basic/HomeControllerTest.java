package my.blog.basic;

import my.blog.board.domain.BoardRepository;
import my.blog.board.dto.response.BoardResponse;
import my.blog.board.service.BoardLookupServiceImpl;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.factory.BaseIntegrationTest;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static my.blog.factory.EntityFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HomeControllerTest extends BaseIntegrationTest {

    private User user;
    private Category category;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(newUserInstance(null, Role.ADMIN));
        category = categoryRepository.save(newCategoryInstance("테스트카테고리", null, null));
    }

    @Test
    void 홈화면_테스트() throws Exception {
        //given
        boardRepository.save(newBoardInstance(user, category, null));
        boardRepository.save(newBoardInstance(user, category, null));
        boardRepository.save(newBoardInstance(user, category, null));
        boardRepository.save(newBoardInstance(user, category, null));
        boardRepository.save(newBoardInstance(user, category, null));
        boardRepository.save(newBoardInstance(user, category, null));

        //when
        ResultActions resultActions = mvc.perform(get("/"));

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}