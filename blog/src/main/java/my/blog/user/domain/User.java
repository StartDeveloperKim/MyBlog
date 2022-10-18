package my.blog.user.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.domain.Board;
import my.blog.comments.domain.Comments;
import my.blog.heart.domain.Heart;
import my.blog.user.dto.OAuthRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING) // String으로 저장
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    protected User() {
    }
    private User(OAuthRequest oAuthRequest) {
        this.email = oAuthRequest.getEmail();
        this.name = oAuthRequest.getEmail();
        this.picture = oAuthRequest.getPicture();
        this.role = Role.GUEST; // 기본은 GUEST, 블로그 주인은 USER
    }

    public static User of(OAuthRequest oAuthRequest) {
        return new User(oAuthRequest);
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
