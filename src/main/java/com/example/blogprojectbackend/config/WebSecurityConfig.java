package com.example.blogprojectbackend.config;

import com.example.blogprojectbackend.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    @Bean
    public WebSecurityCustomizer configure() { // 시큐리티 설정 사용자 정의화

        return (web) -> web.ignoring() // 시큐리티의 인증, 인가 모든 곳에 적용하지 않음
                .requestMatchers(toH2Console()) // H2 DB 관련
                .requestMatchers("/static/**"); // 정적메서드
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception { // HTTP 요청에 대한 시큐리티 설정
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers("/login", "/signup", "/user").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login
                            .loginPage("/login")
                            .defaultSuccessUrl("/articles");
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true); // 로그아웃 시 세션 무효
                })
                .authenticationProvider(daoAuthenticationProvider());


        return httpSecurity.build();
    }

    // DaoAuthenticationProvider: UserDetailsService 및 PasswordEncoder 를 사용하여 사용자 아이디와 암호를 인증하는 AuthenticationProvider 구현체입니다.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { // 패스워드 인코더
        return new BCryptPasswordEncoder();
    }

}
