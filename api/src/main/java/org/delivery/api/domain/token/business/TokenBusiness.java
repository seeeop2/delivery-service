package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.model.TokenDto;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    // 1. userEntity 에서 userId 추출
    // 2. access, refresh token 발생
    // 3. converter 로 tokenResponse 변경

    public TokenResponse issueToken(UserEntity userEntity) {

        // userEntity가 null이 아닐 경우
        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    // 사용자 ID 추출
                    return ue.getId();
                })
                .map(userId -> {
                    // 사용자 ID를 기반으로 액세스 토큰과 리프레시 토큰 발급
                    TokenDto accessToken = tokenService.issueAccessToken(userId);
                    TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                    // 발급된 토큰을 TokenResponse로 변환
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                // userEntity가 null일 경우 예외 발생
                .orElseThrow(
                        () -> new ApiException(ErrorCode.NULL_POINT)
                );
    }

}
