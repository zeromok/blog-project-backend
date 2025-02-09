package com.example.blogprojectbackend.config;

import java.io.IOException;
import java.net.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	/*
	* Spring Security 필터
	* 인증이 안된 익명의 사용자가 인증이 필요한 엔드포인트로 접근하게 된다면
	* Spring Security 의 기본 설정으로는 HttpStatus 401과 함께 스프링의 기본 오류페이지를 보여준다.
	* 기본 오류 페이지가 아닌 커스텀 오류 페이지를 보여준다거나, 특정 로직을 수행 또는 JSON 데이터 등으로 응답해야 하는 경우
	* 예) 인증되지 않은 사용자가 보호된 리소스에 접근 시 인증되지 않았다는 걸 알려주고 어디에서 인증을 수행해야하는지 알려준다.
	* 로그인페이지 리다이렉트 or 인증을 위한 다른 경로 제공
	* */

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
	}
}
