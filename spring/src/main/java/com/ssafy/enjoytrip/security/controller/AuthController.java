package com.ssafy.enjoytrip.security.controller;

import com.ssafy.enjoytrip.member.model.dto.LoginVo;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.dto.MemberType;
import com.ssafy.enjoytrip.member.model.dto.SignupVo;
import com.ssafy.enjoytrip.member.model.service.MemberService;
import com.ssafy.enjoytrip.member.model.service.RefreshTokenService;
import com.ssafy.enjoytrip.security.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.refreshToken.expire.seconds}")
    private Long refreshTokenExpireSeconds;

    private final String TOKEN_PREFIX = "Bearer ";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVo loginVo) {

        MemberDto member = memberService.loginMember(loginVo);

        if (member != null) {

            String jwtAccessToken = jwtUtils.generateAccessToken(member);
            String jwtRefreshToken = jwtUtils.generateRefreshToken();

            // Redis에 Refresh Token 저장
            refreshTokenService.setData(member.getUsername(), jwtRefreshToken);
            log.info("refresh token store | {} : {}", member.getUsername(), jwtRefreshToken);

            // Access Token을 Response Header에 추가
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", TOKEN_PREFIX + jwtAccessToken);

            // Refresh Token을 Response HttpOnly Cookie에 추가
            ResponseCookie cookie = ResponseCookie.from("refreshToken", jwtRefreshToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(refreshTokenExpireSeconds)
                    .build();

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        } else {
            throw new BadCredentialsException("인증 정보가 잘못되었습니다.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupVo signupVo) {

        log.info("controller signup : {}", signupVo);
        
        if (!memberService.duplicateCheck(signupVo.getUsername())) {
        	return new ResponseEntity<String>("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);
        }
        
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(signupVo.getUsername());
        memberDto.setPassword(signupVo.getPassword());
        memberDto.setEmail(signupVo.getEmail());
        memberDto.setType(MemberType.USER);
        memberService.registMember(memberDto);
        Map<String, Integer> response = new HashMap<>();
        response.put("userId", memberDto.getUserId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private long getCurrentTimeInSeconds() {
        return Instant.now().getEpochSecond();
    }
}
