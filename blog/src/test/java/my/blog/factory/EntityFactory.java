package my.blog.factory;

import my.blog.board.domain.Board;
import my.blog.boardTag.domain.BoardTag;
import my.blog.category.domain.Category;
import my.blog.comments.domain.Comments;
import my.blog.heart.domain.Heart;
import my.blog.tag.domain.Tag;
import my.blog.temporalBoard.domain.TemporalBoard;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.dto.OAuthRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class EntityFactory {

    private EntityFactory() {
        throw new RuntimeException();
    }

    public static Board newBoardInstance(User user, Category category, Long id) {
        Board board = Board.newInstance(user, category, "테스트" + id, "테스트 글" + id, null);
        ReflectionTestUtils.setField(board, "createDate", LocalDateTime.now());
        setId(id, board);
        return board;
    }

    public static Category newCategoryInstance(String name, Long id, Long parentId) {
        Category category = Category.newInstance(name, parentId);
        setId(id, category);
        return category;
    }

    public static User newUserInstance(Long id, Role role) {
        User user = User.newInstance(OAuthRequest.builder()
                .picture(null)
                .email("email")
                .attributes(null)
                .name(role == Role.ADMIN ?"김어드민":"김게스트")
                .build());
        setId(id, user);
        if (role == Role.ADMIN) {
            ReflectionTestUtils.setField(user, "role", Role.ADMIN); // 기본 GUEST
        }
        return user;
    }

    public static Tag newTagInstance(Long id, String name) {
        Tag tag = Tag.newInstance(name);
        setId(id, tag);
        return tag;
    }

    public static BoardTag newBoardTagInstance(Long id, Board board, Tag tag) {
        BoardTag boardTag = BoardTag.newInstance(board, tag);
        setId(id, boardTag);
        return boardTag;
    }

    public static Comments newCommentsInstance(Board board, User user, Long id, Long parentId) {
        Comments comment = Comments.newInstance(board, user, "댓글" + id, parentId);
        setId(id, comment);
        return comment;
    }

    public static Heart newHeartInstance(Board board, User user, Long id) {
        Heart heart = Heart.newInstance(user, board);
        setId(id, heart);
        return heart;
    }

    public static TemporalBoard newTemporalBoard(Long id, Long categoryId) {
        TemporalBoard temporalBoard = TemporalBoard.newInstance("임시저장 title", "임시저장 content", null, null, categoryId);
        setId(id, temporalBoard);
        return temporalBoard;
    }

    private static void setId(Long id, Object object) {
        if (id != null) {
            ReflectionTestUtils.setField(object, "id", id);
        }
    }


}
