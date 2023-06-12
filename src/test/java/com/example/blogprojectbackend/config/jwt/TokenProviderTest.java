package com.example.blogprojectbackend.config.jwt;

import com.example.blogprojectbackend.domain.User;
import com.example.blogprojectbackend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @Test
    @DisplayName("generateToken(): 유저정보와 만료기간을 전달해 토큰을 만들 수 있다.")
    void generateToken() {

        /**
         * Given: 토큰에 유저정보를 추가하기위한 테스트유저 생성
         * */
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        /**
         * When: 토큰제공자(tokenProvider)의 메서드를 호출해 토큰 생성
         * */
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        /**
         * Then: 토큰을 복호화 한 후 Claims 의 id 값을 가져온 후 테스트유저의 id 값과 비교
         * */
        Long userID = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody().get("id", Long.class);

        assertThat(userID).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("validToken(): 만료된 토큰일 때 유효성 검사에 실패한다.")
    void validToken_invalidToken() {
        
        /**
         * Given: mocking 객체를 사용해 이미 만료된 토큰 생성
         * */
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        /**
         * When: 토큰제공자의 유효성 검사 메서드에 넘겨줌
         * */
        boolean result = tokenProvider.validToken(token);

        /**
         * Then: false 인지 확인
         * */
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    void getAuthentication() {
        /**
         * Given: subject 가 "user@email.com" 인 토큰 생성
         * */
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);


        /**
         * When: getAuthentication() 을 호출해 인증 객체 반환받음
         * */
        Authentication authentication = tokenProvider.getAuthentication(token);

        /**
         * Then: 반환받은 인증객체의 subject 를 위 subject 와 비교
         * */
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);

    }

    @Test
    @DisplayName("getUserId(): 토큰으로 유저 ID 를 가져올 수 있다.")
    void getUserId() {

        /**
         * Given: claims {id: 1L} 을 추가한 토큰 생성
         * */
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        /**
         * When: 토큰의 id 추출
         * */
        Long userIdByToken = tokenProvider.getUserId(token);


        /**
         * Then: 토큰의 id 비교
         * */
        assertThat(userIdByToken).isEqualTo(userId);
    }

} // end
