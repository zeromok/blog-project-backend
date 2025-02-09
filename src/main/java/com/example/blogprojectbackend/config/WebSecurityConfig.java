package com.example.blogprojectbackend.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.blogprojectbackend.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
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
                    .failureHandler(new CustomAuthenticationFailureHandler()) // 로그인 실패 시 핸들러 등록
                    .defaultSuccessUrl("/articles");

            })
            .logout(logout -> {
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true); // 로그아웃 시 세션 무효
            })
            .authenticationProvider(daoAuthenticProvider());


        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // 패스워드 인코더 등록
        return new BCryptPasswordEncoder();
    }

}
