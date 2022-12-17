package my.blog.factory.mockEntity;

import lombok.Builder;
import lombok.Getter;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;

@Getter
public class MockUser extends User {

    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public MockUser(Long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.email = "email";
        this.picture = "picture";
        this.role = role;
    }

}
