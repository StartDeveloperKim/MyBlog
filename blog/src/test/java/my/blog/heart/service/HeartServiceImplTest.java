package my.blog.heart.service;

import my.blog.board.dto.request.BoardRegister;
import my.blog.board.service.BoardService;
import my.blog.category.domain.Category;
import my.blog.category.dto.CategoryDto;
import my.blog.category.service.CategoryService;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HeartServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    HeartService heartService;
    @Autowired
    CategoryService categoryService;

    @Test
    void 좋아요_추가_삭제_테스트() {
        //when
        User user = new User("ktw", "킴");
        User saveUser = userRepository.save(user);

        categoryService.saveCategory("스프링");
        Category findCategory = categoryService.getCategoryByName("스프링");

        BoardRegister boardRegister = new BoardRegister(saveUser.getId(), "테스트", "테스트입니다.", findCategory.getCategoryName(),
                null, null);
        Long boardId = boardService.writeBoard(boardRegister);

        //given
        heartService.saveHeart(saveUser.getId(), boardId); // 좋아요 저장

        //then
        Long saveResult = heartService.getHeartCount(boardId);
        assertThat(saveResult).isEqualTo(1);

        heartService.deleteHeart(saveUser.getId(), boardId);
        Long removeResult = heartService.getHeartCount(boardId);
        assertThat(removeResult).isEqualTo(0);
    }
}