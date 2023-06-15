package com.example.blogprojectbackend.config.oauth2;

import com.example.blogprojectbackend.domain.User;
import com.example.blogprojectbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 리소스 서버에서 보내주는 사용자 정보를 불러오는 메서드

        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);

        return user;
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        // 사용자 정보가 들어있는 매개변수를 통해 users 테이블에 있으면 update, 없으면 생성하는 메서드

        Map<String, Object> attribute = oAuth2User.getAttributes(); // 사용자 정보를 맵으로 변환

        String email = (String) attribute.get("email");
        String name = (String) attribute.get("name");

        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name)) // 있으면 update
                .orElse(User.builder() // 없으면 생성
                        .email(email)
                        .nickname(name)
                        .build());

        return userRepository.save(user);
    }

} // end
