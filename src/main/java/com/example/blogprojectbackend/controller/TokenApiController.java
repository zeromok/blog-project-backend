package com.example.blogprojectbackend.controller;

import com.example.blogprojectbackend.dto.CreatedAccessTokenRequest;
import com.example.blogprojectbackend.dto.CreatedAccessTokenResponse;
import com.example.blogprojectbackend.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreatedAccessTokenResponse> createNewAccessToken(@RequestBody CreatedAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewToken(request.getRefreshToken());

        log.info("{}", new CreatedAccessTokenResponse(newAccessToken));
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedAccessTokenResponse(newAccessToken));
    }

}
