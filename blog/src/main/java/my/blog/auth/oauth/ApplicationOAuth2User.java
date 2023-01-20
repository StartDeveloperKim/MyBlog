package my.blog.auth.oauth;

import my.blog.user.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ApplicationOAuth2User implements OAuth2User {

    private final String id;
    private final Role role;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public ApplicationOAuth2User(String id, Role role, Map<String, Object> attributes) {
        this.id = id;
        this.role = role;
        this.attributes = attributes;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getRole() {
        return this.role.getKey();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.id;
    }
}
