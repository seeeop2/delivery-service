package org.delivery.api.domain.token.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    // 액세스 토큰
    private String accessToken;

    // 액세스 토큰의 만료 시간
    private LocalDateTime accessTokenExpiresAt;

    // 리프레시 토큰
    private String refreshToken;

    // 리프레시 토큰의 만료 시간
    private LocalDateTime refreshTokenExpiresAt;

}
