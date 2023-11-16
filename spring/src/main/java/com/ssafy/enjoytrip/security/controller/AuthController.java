package com.ssafy.enjoytrip.security.controller;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.member.model.dto.LoginVo;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.dto.MemberType;
import com.ssafy.enjoytrip.member.model.dto.SignupVo;
import com.ssafy.enjoytrip.model.service.MemberService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @Value("${jwt.signing.key}")
    private String signingKey;

    private final String TOKEN_PREFIX = "Bearer ";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVo loginVo) {

        log.info("login controller, VO : {}", loginVo);
        MemberDto member = memberService.loginMember(loginVo);

        if (member != null) {
            // JWT 서명 키
            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
            log.info("k : {}", key);
            /**
             * JWT Claims
             * iss (Issuer)         : 토큰을 발행한 주체(예: 웹 서비스의 도메인).
             * aud (Audience)       : 토큰의 수신자 또는 대상 audience.
             * exp (Expiration Time): 토큰의 만료 시간. 일반적으로 UNIX 시간 형식으로 지정.
             * nbf (Not Before)     : 토큰이 활성화되는 시간. 이 시간 이전에는 토큰이 유효하지 않음.
             * iat (Issued At)      : 토큰이 발행된 시간. 자동으로 변경되어 토큰마다 다른 값을 가짐.
             * jti (JWT ID)         : 토큰의 고유 식별자. 중복을 방지하는 데 사용될 수 있음.
             * userId               : 사용자 식별자
             * username				: 사용자 로그인에 사용되는 아이디
             */
            Map<String, Object> map = new HashMap<>();
            long currentTime = getCurrentTimeInSeconds();
            map.put("iss", "http://localhost:8080");
            map.put("aud", "http://localhost:5173");
            map.put("exp", currentTime + 900);
            map.put("nbf", currentTime);
            map.put("iat", currentTime);
            map.put("jti", "enjoytrip-auth-jwt");
            map.put("userId", member.getUserId());
            map.put("username", member.getUsername());
            map.put("userType", member.getType());
log.info("exp : {}", currentTime + 900);
            // JWT 생성
            String jwtToken = Jwts.builder()
                    .setClaims(map)
                    .signWith(key)
                    .compact();

            return ResponseEntity.ok()
                    .header("Authorization", TOKEN_PREFIX + jwtToken)
                    .build();
        } else {
            throw new BadCredentialsException("인증 정보가 잘못되었습니다.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupVo signupVo) {

        log.info("controller signup : {}", signupVo);
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(signupVo.getUsername());
        memberDto.setPassword(signupVo.getPassword());
        memberDto.setEmail(signupVo.getEmail());
        memberDto.setType(MemberType.ADMIN);
        memberService.registMember(memberDto);
        Map<String, Integer> response = new HashMap<>();
        response.put("userId", memberDto.getUserId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private long getCurrentTimeInSeconds() {
        return Instant.now().getEpochSecond();
    }
}
