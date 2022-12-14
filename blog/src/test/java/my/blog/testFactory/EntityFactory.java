package my.blog.testFactory;

import my.blog.board.domain.Board;
import my.blog.boardTag.domain.BoardTag;
import my.blog.category.domain.Category;
import my.blog.comments.domain.Comments;
import my.blog.heart.domain.Heart;
import my.blog.tag.domain.Tag;
import my.blog.temporalBoard.domain.TemporalBoard;
import my.blog.user.domain.User;
import my.blog.user.dto.OAuthRequest;

import java.util.UUID;

public class EntityFactory {

    private EntityFactory() {
        throw new RuntimeException();
    }

    public static Board newBoardInstance(User user, Category category) {
        return Board.newInstance(user, category, getRandomUUIDString(), getRandomUUIDString(), getRandomUUIDString());
    }

    public static Category newCategoryInstance(Long parentCategoryId) {
        return Category.newInstance("category", parentCategoryId);
    }

    public static User newUserInstance() {
        return User.newInstance(OAuthRequest.builder()
                .picture(null)
                .email(getRandomUUIDString())
                .name(getRandomUUIDString())
                .attributes(null)
                .nameAttributeKey(null)
                .build());
    }

    public static Tag newTagInstance(String name) {
        return Tag.newInstance(name);
    }

    public static BoardTag newBoardTagInstance(Board board, Tag tag) {
        return BoardTag.newInstance(board, tag);
    }

    public static Comments newCommentsInstance(Board board, User user, Long parentCommentId) {
        return Comments.newInstance(board, user, getRandomUUIDString(),parentCommentId);
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
