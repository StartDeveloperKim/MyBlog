package my.blog.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import my.blog.user.domain.Role;

@ToString
@Getter
public class RecognizeUser {

    private final String email;
    private final Role role;

    @Builder
    public RecognizeUser(String email, String role) {
        this.email = email;
        this.role = Role.toRole(role);
    }
}
