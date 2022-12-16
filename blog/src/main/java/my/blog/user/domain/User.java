package my.blog.user.domain;

import lombok.Getter;
import my.blog.BaseTimeEntity;
import my.blog.board.domain.Board;
import my.blog.heart.domain.Heart;
import my.blog.user.dto.OAuthRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
//@Table(name = "Users")
public class User extends BaseTimeEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    /*@OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();*/

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    protected User() {
    }

    public static User newInstance(OAuthRequest oAuthRequest) {
        User user = new User();
        user.email = oAuthRequest.getEmail();
        user.name = oAuthRequest.getName();
        user.picture = oAuthRequest.getPicture();
        user.role = Role.GUEST;
        return user;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
