package my.blog.config;

import lombok.RequiredArgsConstructor;
import my.blog.user.domain.Role;
import my.blog.user.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*권한에 따른 접근페이지 조정 필요 현재는 개발단계이기에 모두 열어놨음
        * 현재 날짜 2022-10-21
        * */
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
//                .antMatchers(HttpMethod.POST, "/board").hasRole(Role.USER.name())
//                .antMatchers(HttpMethod.PUT, "/board/**").hasRole(Role.USER.name())
//                .antMatchers(HttpMethod.DELETE, "/board/**").hasRole(Role.USER.name())
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        return http.build();
    }
}
