package my.blog.user.service;

import lombok.RequiredArgsConstructor;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.dto.OAuthRequest;
import my.blog.user.dto.SessionUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

import static my.blog.user.service.SessionConst.*;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthRequest oAuthRequest = OAuthRequest.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(oAuthRequest);

        httpSession.setAttribute(LOGIN_USER, new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                oAuthRequest.getAttributes(),
                oAuthRequest.getNameAttributeKey());
    }

    // ?????? ????????? ??????????????? ???????????? but ?????????????????? ?????? ??????????????? ????????? ???????????? ??? persist
    // ????????? ????????? JPA?????? save ????????? ??????????????? persist??? merge??? ????????????.
    private User saveOrUpdate(OAuthRequest oAuthRequest) {
        User user = userRepository.findByEmail(oAuthRequest.getEmail()).orElse(oAuthRequest.toEntity());
        return userRepository.save(user);
    }
}
