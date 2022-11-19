package my.blog.board.service;

import my.blog.board.domain.BoardRepository;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.CategoryRepository;
import my.blog.tag.domain.TagRepository;
import my.blog.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    BoardTagRepository boardTagRepository;

    @Test
    void 태그가있는_게시글을_저장한다() {

    }

    @Test
    void 태그가없는_게시글을_저장한다() {

    }

}