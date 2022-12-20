package my.blog.boardTag;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.boardTag.domain.BoardTag;
import my.blog.boardTag.domain.BoardTagRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.factory.EntityFactory;
import my.blog.tag.domain.Tag;
import my.blog.tag.domain.TagRepository;
import my.blog.tag.dto.TagCountResponse;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static my.blog.factory.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class BoardTagRepositoryTest {
    
    @Autowired
    private BoardTagRepository boardTagRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;

    private User user;
    private Category category;
    private Tag tag;

    private Board savedBoard1;
    private Board savedBoard2;
    private Board savedBoard3;

    private final static String TAG_NAME = "테스트태그";

    @BeforeEach
    void setUp() {
        user = userRepository.save(newUserInstance(null, Role.ADMIN));
        category = categoryRepository.save(newCategoryInstance("카테고리", null, null));
        tag = tagRepository.save(newTagInstance(null, TAG_NAME));

        savedBoard1 = boardRepository.save(newBoardInstance(user, category, null));
        savedBoard2 = boardRepository.save(newBoardInstance(user, category, null));
        savedBoard3 = boardRepository.save(newBoardInstance(user, category, null));
    }
    @Test
    void TagName이_태그된_게시글을_게시글ID_내림차순으로_조회한다() {
        //given
        savedBoardTagEntity(savedBoard1, tag);
        savedBoardTagEntity(savedBoard2, tag);

        PageRequest pageRequest = PageRequest.of(0, 6);
        //when
        List<BoardTag> boardTags = boardTagRepository.findBoardTagByTagTagName(pageRequest, TAG_NAME);

        //then
        assertThat(boardTags.get(0).getBoard().getId()).isEqualTo(savedBoard2.getId());
        assertThat(boardTags.get(1).getBoard().getId()).isEqualTo(savedBoard1.getId());
    }

    @Test
    void 태그가_달린_게시글의_개수를_조회한다() {
        //given
        savedBoardTagEntity(savedBoard1, tag);
        savedBoardTagEntity(savedBoard2, tag);
        savedBoardTagEntity(savedBoard3, tag);

        //when
        Long result = boardTagRepository.countBoardTagByTagName(TAG_NAME);
        //then
        assertThat(result).isEqualTo(3L);
    }

    @Test
    void 게시글ID를_통해_BoardTag엔티티를_조회() {
        //given
        BoardTag boardTag = savedBoardTagEntity(savedBoard1, tag);
        //when
        List<BoardTag> result = boardTagRepository.findBoardTagsByBoardId(savedBoard1.getId());
        //then
        assertThat(boardTag.getId()).isEqualTo(result.get(0).getId());
        assertThat(boardTag.getTag().getTagName()).isEqualTo(TAG_NAME);
    }

    private BoardTag savedBoardTagEntity(Board board, Tag tag) {
        return boardTagRepository.save(newBoardTagInstance(null, board, tag));
    }
}
