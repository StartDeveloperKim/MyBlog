package my.blog.user.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.domain.Board;
import my.blog.comments.domain.Comments;
import my.blog.heart.domain.Heart;

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
    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.picture = "dummy";
        this.role = Role.USER;
    }
}
