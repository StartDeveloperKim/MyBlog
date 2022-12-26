package my.blog.comments.domain;

import my.blog.board.domain.Board;
import my.blog.board.domain.BoardRepository;
import my.blog.category.domain.Category;
import my.blog.category.domain.CategoryRepository;
import my.blog.factory.EntityFactory;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static my.blog.factory.EntityFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentsRepositoryTest {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private Board board;
    private Category category;
    @BeforeEach
    void setUp() {
        user = userRepository.save(newUserInstance(null, Role.ADMIN));
        category = categoryRepository.save(newCategoryInstance("카테고리", null, null));
        board = boardRepository.save(newBoardInstance(user, category, null));
    }

    @Test
    void 게시글에_대한_댓글_리스트를_조회한다() {
        //given
        Comments comment1 = commentsRepository.save(newCommentsInstance(board, user, null, null));
        Comments comment2 = commentsRepository.save(newCommentsInstance(board, user, null, null));
        //when
        List<Comments> result = commentsRepository.findCommentsByBoardId(board.getId());
        //then
        Comments findComment1 = result.get(0);
        assertThat(comment1.getId()).isEqualTo(findComment1.getId());
        Comments findComment2 = result.get(1);
        assertThat(comment2.getId()).isEqualTo(findComment2.getId());
    }

    @Test
    void 게시글Id를_통해_게시글의_댓글_수를_조회한다() {
        //given
        commentsRepository.save(newCommentsInstance(board, user, null, null));
        //when
        int result = commentsRepository.getCommentsCountByBoardId(board.getId());
        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void 부모댓글을_삭제하면_대댓글까지_삭제된다() {
        //given
        Comments parentComment = commentsRepository.save(newCommentsInstance(board, user, null, null));
        Comments childComment = commentsRepository.save(newCommentsInstance(board, user, null, parentComment.getId()));
        //when
        commentsRepository.deleteParentComment(parentComment.getId(), board.getId());
        //then
        List<Comments> result = commentsRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }
}