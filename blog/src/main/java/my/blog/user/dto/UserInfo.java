package my.blog.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class UserInfo {

    private Long userId;
    private String name;
}
