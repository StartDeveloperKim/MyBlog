package my.blog.config;

import lombok.RequiredArgsConstructor;
import my.blog.auth.jwt.JwtAuthenticationFilter;
import my.blog.auth.oauth.OAuthSuccessHandler;
import my.blog.user.domain.Role;
import my.blog.user.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable() // csrf  설정 꺼놓기
                .headers().frameOptions().disable()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                                .antMatchers(HttpMethod.GET, "/board/edit").hasRole(Role.ADMIN.getKey())
                                .antMatchers(HttpMethod.POST, "/board").hasRole(Role.ADMIN.getKey())
                                .antMatchers(HttpMethod.DELETE, "/board/**").hasRole(Role.ADMIN.getKey())
                                .antMatchers(HttpMethod.PATCH, "/board/**").hasRole(Role.ADMIN.getKey())
                                .antMatchers(HttpMethod.POST, "/comment/**").authenticated()
                                .antMatchers(HttpMethod.POST, "/category/**").hasRole(Role.ADMIN.getKey())
                .anyRequest().permitAll()

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/*")

                .and()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")

                .and()
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                .userService(customOAuth2UserService)

                .and()
                .successHandler(oAuthSuccessHandler)

                .and()
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }
}
