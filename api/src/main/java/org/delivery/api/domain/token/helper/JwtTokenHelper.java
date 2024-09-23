package org.delivery.api.domain.token.helper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    // JWT 서명에 사용할 비밀 키
    @Value("${token.secret.key}")
    private String secretKey;

    // 액세스 토큰 만료 시간(시간 단위)
    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    // 리프레시 토큰 만료 시간(시간 단위)
    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        // 액세스 토큰 만료 시간을 설정
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // 비밀 키 생성
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // JWT 토큰 생성
        String jwtToken = Jwts.builder()
                // 서명 알고리즘 설정
                .signWith(key, SignatureAlgorithm.HS256)
                // 클레임 설정
                .setClaims(data)
                // 만료 시간 설정 
                .setExpiration(expiredAt)
                // 토큰 생성
                .compact();

        // 생성된 토큰과 만료 시간을 포함한 TokenDto 반환
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        // 리프레시 토큰 만료 시간을 설정
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        // 비밀 키 생성
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // JWT 토큰 생성
        String jwtToken = Jwts.builder()
                // 서명 알고리즘 설정
                .signWith(key, SignatureAlgorithm.HS256)
                // 클레임 설정
                .setClaims(data)
                // 만료 시간 설정
                .setExpiration(expiredAt)
                // 토큰 생성
                .compact();

        // 생성된 토큰과 만료 시간을 포함한 TokenDto 반환
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        // 비밀 키 생성
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // JWT 파서 생성
        JwtParser parser = Jwts.parserBuilder()
                // 서명 키 설정
                .setSigningKey(key)
                .build();

        try {
            // 토큰 검증 및 클레임 파싱
            Jws<Claims> result = parser.parseClaimsJws(token);

            // 검증된 클레임 반환
            return new HashMap<>(result.getBody());

        } catch (Exception e) {
            // 토큰이 유효하지 않을 때
            if (e instanceof SignatureException) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            }
            // 만료된 토큰
            else if (e instanceof ExpiredJwtException) {
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            }
            // 그 외 에러
            else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}
