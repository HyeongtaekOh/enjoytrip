package com.ssafy.enjoytrip.util.controller;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.member.model.service.RefreshTokenService;
import com.ssafy.enjoytrip.security.utils.CookieUtils;
import com.ssafy.enjoytrip.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
			return ResponseEntity.badRequest().build();
		}

		Integer userId = jwtUtils.getUserIdFromJwt(jwt, "userId", Integer.class);
		Optional<MemberDto> m = memberService.getMemberById(userId);

		if (!m.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		MemberDto member = m.get();

		Optional<Cookie> cookie = cookieUtils.getCookie(request, "refreshToken");

		if (!cookie.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		String refreshToken = cookieUtils.deserialize(cookie.get(), String.class);

		if (refreshToken == null) {
			return ResponseEntity.badRequest().build();
		}

		String redisRefreshToken = refreshTokenService.getData(member.getUsername());

		if (!refreshToken.equals(redisRefreshToken)) {
			return ResponseEntity.badRequest().build();
		}

		String newAccessToken = jwtUtils.generateAccessToken(member);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", TOKEN_PREFIX + newAccessToken);

//				.ifPresent(cookie -> {
//			String refreshToken = cookieUtils.deserialize(cookie, String.class);
//			if (refreshToken == null) {
//				return;
//			}
//			String redisRefreshToken = refreshTokenService.getData(username);
//			if (refreshToken.equals(redisRefreshToken)) {
//				String newAccessToken = jwtUtils.generateAccessToken(username);
////				cookieUtils.addCookie(response, "accessToken", newAccessToken, (int) (jwtUtils.getAccessTokenExpiration() / 1000));
//			}
//		}

		ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
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
