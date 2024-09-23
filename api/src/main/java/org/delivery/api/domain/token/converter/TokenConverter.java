package org.delivery.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;

import java.util.Objects;

// 토큰 관련 변환 기능을 제공하는 클래스

@RequiredArgsConstructor
@Converter
public class TokenConverter {

    // TokenDto 객체를 TokenResponse 객체로 변환
    public TokenResponse toResponse(TokenDto accessToken, TokenDto refreshToken) {

        // accessToken, refreshToken 둘 중 null 값이 들어오는 경우를 대비
        Objects.requireNonNull(accessToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        // TokenResponse 객체 생성 및 반환
        return TokenResponse.builder()
                // 액세스 토큰 설정
                .accessToken(accessToken.getToken())
                // 액세스 토큰 만료 시간 설정
                .accessTokenExpiresAt(accessToken.getExpiredAt())
                // 리프레시 토큰 설정
                .refreshToken(refreshToken.getToken())
                // 리프레시 토큰 만료 시간 설정
                .refreshTokenExpiresAt(refreshToken.getExpiredAt())
                .build();
    }

}
