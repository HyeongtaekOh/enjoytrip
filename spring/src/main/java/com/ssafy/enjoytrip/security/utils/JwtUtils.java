package com.ssafy.enjoytrip.security.utils;

import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.accessToken.signing.key}")
    private String accessTokenSigningKey;

    @Value("${jwt.refreshToken.signing.key}")
    private String refreshTokenSigningKey;

    @Value("${jwt.accessToken.expire.seconds}")
    private Long accessTokenExpireSeconds;

    @Value("${jwt.refreshToken.expire.seconds}")
    private Long refreshTokenExpireSeconds;

    private final String TOKEN_PREFIX = "Bearer ";

    public String generateAccessToken(MemberDto member) {
        /* Access Token */
        // JWT 서명 키
        SecretKey accessTokenKey = Keys.hmacShaKeyFor(accessTokenSigningKey.getBytes(StandardCharsets.UTF_8));

        /**
         * JWT Access Token Claims
         * iss (Issuer)         : 토큰을 발행한 주체(예: 웹 서비스의 도메인).
         * aud (Audience)       : 토큰의 수신자 또는 대상 audience.
         * exp (Expiration Time): 토큰의 만료 시간. 일반적으로 UNIX 시간 형식으로 지정.
         * nbf (Not Before)     : 토큰이 활성화되는 시간. 이 시간 이전에는 토큰이 유효하지 않음.
         * iat (Issued At)      : 토큰이 발행된 시간. 자동으로 변경되어 토큰마다 다른 값을 가짐.
         * jti (JWT ID)         : 토큰의 고유 식별자. 중복을 방지하는 데 사용될 수 있음.
         * userId               : 사용자 식별자
         * username				: 사용자 로그인에 사용되는 아이디
         */
        Map<String, Object> accessTokenMap = new HashMap<>();
        long currentTime = getCurrentTimeInSeconds();
        accessTokenMap.put("iss", "http://localhost:8080");
        accessTokenMap.put("aud", "http://localhost:5173");
        accessTokenMap.put("exp", currentTime + accessTokenExpireSeconds);
        accessTokenMap.put("nbf", currentTime);
        accessTokenMap.put("iat", currentTime);
        accessTokenMap.put("jti", "enjoytrip-auth-jwt");
        accessTokenMap.put("userId", member.getUserId());
        accessTokenMap.put("username", member.getUsername());
        accessTokenMap.put("userType", member.getType());
        // JWT 생성
        String jwtAccessToken = Jwts.builder()
                .setClaims(accessTokenMap)
                .signWith(accessTokenKey)
                .compact();

        return jwtAccessToken;
    }

    public String generateRefreshToken() {
        /* Refresh Token */
        // JWT Refresh Token 서명 키
        SecretKey refreshTokenKey = Keys.hmacShaKeyFor(accessTokenSigningKey.getBytes(StandardCharsets.UTF_8));

        /**
         * JWT Refresh Token Claims
         * exp(Expiration Time) : 토큰의 만료 시간. 일반적으로 UNIX 시간 형식으로 지정.
         */
        Map<String, Object> refreshTokenMap = new HashMap<>();
        long currentTime = getCurrentTimeInSeconds();
        refreshTokenMap.put("exp", currentTime + refreshTokenExpireSeconds);
        // JWT 생성
        String jwtRefreshToken = Jwts.builder()
                .setClaims(refreshTokenMap)
                .signWith(refreshTokenKey)
                .compact();

        return jwtRefreshToken;
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        String jwt = header.substring(TOKEN_PREFIX.length());

        if (!isValidJwtFormat(jwt)) {
            return null;
        }

        return jwt;
    }

    public <T> T getUserIdFromJwt(String jwt, String claimName, Class<T> requiredType) {
        SecretKey accessKey = Keys.hmacShaKeyFor(accessTokenSigningKey.getBytes(StandardCharsets.UTF_8));
        Claims accessClaims = null;
        try {
            accessClaims = Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }

        return accessClaims.get(claimName, requiredType);
    }

    private boolean isValidJwtFormat(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        String[] parts = token.split("\\.");
        return parts.length == 3;
    }

    private long getCurrentTimeInSeconds() {
        return Instant.now().getEpochSecond();
    }
}
