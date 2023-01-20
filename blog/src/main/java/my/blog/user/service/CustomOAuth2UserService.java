package my.blog.user.service;

import lombok.extern.slf4j.Slf4j;
import my.blog.auth.oauth.ApplicationOAuth2User;
import my.blog.user.domain.User;
import my.blog.user.domain.UserRepository;
import my.blog.user.dto.OAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        log.info("registrationId : {}, userNameAttributeName : {}", registrationId, userNameAttributeName);
        OAuthRequest oAuthRequest = OAuthRequest.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(oAuthRequest);

        return new ApplicationOAuth2User(user.getEmail(), user.getRole(),oAuth2User.getAttributes());

    }

    private User saveOrUpdate(OAuthRequest oAuthRequest) {
        User user = userRepository.findByEmail(oAuthRequest.getEmail()).orElse(oAuthRequest.toEntity());
        return userRepository.save(user);
    }
}
