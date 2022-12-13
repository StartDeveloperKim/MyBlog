package my.blog.config;

import lombok.RequiredArgsConstructor;
import my.blog.user.domain.Role;
import my.blog.user.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import static my.blog.user.domain.Role.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf  설정 꺼놓기
                .headers().frameOptions().disable()
                .and()

                // 페이지와 HttpMethod 별 권한 설정
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/board/edit").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.POST, "/board").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/board/**").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/board/**").hasRole(ADMIN.name())
                .antMatchers(HttpMethod.POST, "/comment/**").authenticated()
                .antMatchers("/category/**").hasRole(ADMIN.name())
                .anyRequest().permitAll()
                .and()

                .logout()
                .logoutSuccessUrl("/")
                .and()

                .oauth2Login()
                .loginPage("/login")
                .failureUrl("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}
