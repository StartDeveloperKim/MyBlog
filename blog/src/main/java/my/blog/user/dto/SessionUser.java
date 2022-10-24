package my.blog.user.dto;

import lombok.Getter;
import my.blog.user.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class SessionUser implements Serializable {

    private Long userId;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
