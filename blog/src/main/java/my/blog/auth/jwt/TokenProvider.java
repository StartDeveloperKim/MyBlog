package my.blog.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.auth.oauth.ApplicationOAuth2User;
import my.blog.user.domain.Role;
import my.blog.user.domain.User;
import my.blog.user.dto.RecognizeUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@PropertySource("classpath:application-jwt.properties")
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public TokenProvider() {
    }

    public String create(User user) {
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(user.getEmail())
                .setSubject(user.getRoleKey())
                .setIssuer("Blog")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String create(final Authentication authentication) {
        ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User) authentication.getPrincipal();
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userPrincipal.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("Role", userPrincipal.getRole())
                .compact();
    }

    public RecognizeUser validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return RecognizeUser.builder()
                .email(claims.getSubject())
                .role((String) claims.get("Role"))
                .build();
    }
}
