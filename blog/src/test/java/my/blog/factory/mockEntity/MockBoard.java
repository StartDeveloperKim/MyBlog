package my.blog.factory.mockEntity;

import lombok.Builder;
import lombok.Getter;
import my.blog.board.domain.Board;
import my.blog.category.domain.Category;
import my.blog.user.domain.User;

import java.time.LocalDateTime;

@Getter
public class MockBoard extends Board {

    private Long id;
    private String title;
    private String content;
    private String thumbnail;
    private Long hit;
    private LocalDateTime createDate;
    private User user;
    private Category category;

    @Builder
    public MockBoard(Long id, String title, String content, LocalDateTime createDate, User user, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnail = "thumbnail";
        this.hit = 0L;
        this.createDate = createDate;
        this.user = user;
        this.category = category;
    }

}
