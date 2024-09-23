package org.delivery.api.domain.token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// JWT(Json Web Token) 인증 정보를 담기 위한 데이터 전송 객체(DTO) 클래스

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    // JWT 토큰 문자열
    private String token;

    // 토큰 만료 시간
    private LocalDateTime expiredAt;

}
