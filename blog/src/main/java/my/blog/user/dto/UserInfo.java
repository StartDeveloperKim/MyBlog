package my.blog.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserInfo {

    private final Long userId;
    private final String name;

    public UserInfo(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
