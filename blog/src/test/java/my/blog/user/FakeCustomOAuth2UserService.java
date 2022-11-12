package my.blog.user;

import my.blog.user.domain.User;
import my.blog.user.dto.OAuthRequest;

import java.util.HashMap;
import java.util.Map;

public class FakeCustomOAuth2UserService {

    public User loadUser() {
        String registrationId = "fakeRegistrationId";
        String userNameAttributeName = "fakeUserNameAttributeName";
        Map<String, Object> map = new HashMap<>();
        map.put("name", "kim");
        map.put("email", "email@email.com");
        map.put("picture", "hihihihi");

        OAuthRequest oAuthRequest = OAuthRequest.of(registrationId, userNameAttributeName, map);
        return oAuthRequest.toEntity();
    }

}
