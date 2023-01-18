package my.blog.auth.oauth;

import lombok.extern.slf4j.Slf4j;
import my.blog.auth.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String LOCAL_REDIRECT_URL = "http://localhost:8077";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenProvider tokenProvider = new TokenProvider();
//        String token = tokenProvider.create(authentication);

        Optional<Cookie> oCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("redirect_url"))
                .findFirst();
        Optional<String> redirectUri = oCookie.map(Cookie::getValue);

//        log.info("token: {}, redirectURL: {}", token, redirectUri);
        response.sendRedirect(redirectUri.orElseGet(() -> LOCAL_REDIRECT_URL));
//        + "/oauthlogin?token=" + token

    }
}
