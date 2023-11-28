package com.ssafy.enjoytrip.util.controller;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.member.model.service.RefreshTokenService;
import com.ssafy.enjoytrip.security.utils.CookieUtils;
import com.ssafy.enjoytrip.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UtilController {

	private final JwtUtils jwtUtils;
	private final CookieUtils cookieUtils;
	private final MemberService memberService;
	private final RefreshTokenService refreshTokenService;

	@Value("${jwt.refreshToken.expire.seconds}")
	private Long refreshTokenExpireSeconds;

	private final String TOKEN_PREFIX = "Bearer ";

	@GetMapping("/validate-token")
	public ResponseEntity<Void> validJwt() {
		return ResponseEntity.ok(null);
	}

	@GetMapping("/refresh-token")
	public ResponseEntity<Void> refreshJwt(HttpServletRequest request) {

		String jwt = jwtUtils.extractToken(request);

		if (jwt == null) {
			log.info("refresh token | jwt is null");
			return ResponseEntity.badRequest().build();
		}

		Integer userId = jwtUtils.getUserIdFromExpiredJwt(jwt, "userId", Integer.class);
		
		if (userId == null) {
			log.info("refresh token | claims do not have userId field");
			return ResponseEntity.badRequest().build();
		}
		
		Optional<MemberDto> m = memberService.getMemberById(userId);

		if (!m.isPresent()) {
			log.info("refresh token | member does not exist | userId = {}", userId);
			return ResponseEntity.badRequest().build();
		}

		MemberDto member = m.get();

		Optional<Cookie> cookie = cookieUtils.getCookie(request, "refreshToken");

		if (!cookie.isPresent()) {
			log.info("refresh token | refreshToken cookie does not exist");
			return ResponseEntity.badRequest().build();
		}

		String refreshToken = cookie.get().getValue();
		log.info("deserialized refresh token = {}", refreshToken);

		if (refreshToken == null) {
			log.info("refresh token | get refreshToken value fail");
			return ResponseEntity.badRequest().build();
		}
		
		if (jwtUtils.isExpiredRefreshToken(refreshToken)) {
			log.info("refresh token | refresh token is expired");
			return ResponseEntity.badRequest().build();
		}

		String storedRefreshToken = refreshTokenService.getData(member.getUsername());
		log.info("stored refresh token = {}", storedRefreshToken);

		if (!refreshToken.equals(storedRefreshToken)) {
			log.info("refresh token | refresh token is not matched");
			return ResponseEntity.badRequest().build();
		}

		String newAccessToken = jwtUtils.generateAccessToken(member);
		String newRefreshToken = jwtUtils.generateRefreshToken();
		
		refreshTokenService.setData(member.getUsername(), newRefreshToken);
		log.info("updated refresh token store | {} : {}", member.getUsername(), newRefreshToken);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", TOKEN_PREFIX + newAccessToken);

		ResponseCookie responseCookie = ResponseCookie.from("refreshToken", newRefreshToken)
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(refreshTokenExpireSeconds)
				.build();

		return ResponseEntity.ok()
				.headers(responseHeaders)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.build();
	}
}
