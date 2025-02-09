package com.example.blogprojectbackend.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws AuthenticationException, IOException {
		/*
		* Spring Security 에서 인증 실패 시 호출되는 필터
		* 로그인 시 인증에 실패한 경우 사용
		* */

		if (exception instanceof UsernameNotFoundException) {
			response.sendRedirect("/login?error=username_not_found");
		} else if (exception instanceof BadCredentialsException) {
			response.sendRedirect("/login?error=wrong_password");
		} else if (exception instanceof LockedException) {
			response.sendRedirect("/login?error=account_locked");
		} else if (exception instanceof DisabledException) {
			response.sendRedirect("/login?error=account_disabled");
		} else {
			response.sendRedirect("/login?error=unknown_error");
		}

		// 상태 코드 및 응답 설정
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");


		// 응답 출력
		response.getWriter().flush();
	}



}
