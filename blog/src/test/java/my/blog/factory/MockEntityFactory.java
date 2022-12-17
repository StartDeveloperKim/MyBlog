package my.blog.factory;

import my.blog.board.domain.Board;
import my.blog.boardTag.domain.BoardTag;
import my.blog.category.domain.Category;
import my.blog.comments.domain.Comments;
import my.blog.factory.mockEntity.*;
import my.blog.heart.domain.Heart;
import my.blog.tag.domain.Tag;
import my.blog.temporalBoard.domain.TemporalBoard;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class MockEntityFactory {

    private MockEntityFactory() {
        throw new RuntimeException();
    }

    public static Board newBoardInstance(User user, Category category, Long id) {
        return MockBoard
                .builder()
                .id(id)
                .title("테스트" + id)
                .content("테스트글" + id)
                .user(user)
                .category(category)
                .createDate(LocalDateTime.now()).build();
    }

    public static Board newBoardInstance(Long id) {
        return MockBoard
                .builder()
                .id(id)
                .title("테스트" + id)
                .content("테스트글" + id)
                .createDate(LocalDateTime.now()).build();
    }

    public static Category newCategoryInstance(String isParentCategory) {
        if (isParentCategory.equalsIgnoreCase("PARENT")) {
            return MockCategory.builder()
                    .id(1L)
                    .categoryName("부모카테고리")
                    .parentId(null)
                    .build();
        } else {
            return MockCategory.builder()
                    .id(2L)
                    .categoryName("자식카테고리")
                    .parentId(1L)
                    .build();
        }
    }

    public static User newUserInstance(Role role) {
        return MockUser.builder()
                .id(1L)
                .name(role == Role.ADMIN ? "김어드민" : "김게스트")
                .role(role).build();
    }

    public static Tag newTagInstance(Long id, String name) {
        return new MockTag(id, name);
    }

    public static BoardTag newBoardTagInstance(Long id, Board board, Tag tag) {
        return new MockBoardTag(id, board, tag);
    }

    public static Comments newCommentsInstance(Board board, User user, Long parentCommentId) {
        return Comments.newInstance(board, user, getRandomUUIDString(), parentCommentId);
    }

    public static Heart newHeartInstance(Board board, User user) {
        return Heart.newInstance(user, board);
    }

    public static TemporalBoard newTemporalBoard(Long categoryId) {
        return TemporalBoard.newInstance("임시저장 title", "임시저장 content", null, null, categoryId);
    }

    private static String getRandomUUIDString() {
        return UUID.randomUUID().toString();
    }


}
