package my.blog.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    ADMIN("ROLE_ADMIN", "주인");

    private final String key;
    private final String title;

    public static Role toRole(String role) {
        switch (role) {
            case "ROLE_GUEST":
                return GUEST;
            case "ROLE_ADMIN":
                return ADMIN;
        }
        return null;
    }
}
