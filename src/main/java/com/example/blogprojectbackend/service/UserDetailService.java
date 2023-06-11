package com.example.blogprojectbackend.service;

import com.example.blogprojectbackend.domain.User;
import com.example.blogprojectbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    // UserDetailsService: 스프링 스큐리티에서 사용자의 정보를 가져오는 인터페이스

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));
    }

}
