package my.blog.user.dto;

import lombok.Builder;
import lombok.Getter;
import my.blog.user.domain.User;

import java.util.Map;

@Getter
public class OAuthRequest {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    private OAuthRequest(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthRequest of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // 2022-10-18 현재는 구글로그인만 연동해놨기에 ofGoogle 하나만 구현했지만
        // 네이버 로그인까지 연동한다면 registrationId를 사용해서 naver로그인인지, google로그인인지 구분해야한다.
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthRequest ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthRequest.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.of(this);
    }
}
