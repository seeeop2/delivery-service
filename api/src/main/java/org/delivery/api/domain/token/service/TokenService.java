package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Token 에 대한 도메인 로직

@RequiredArgsConstructor
@Service
public class TokenService {

    // TokenHelperIfs 인터페이스의 구현체 주입
    private final TokenHelperIfs tokenHelperIfs;

    // 사용자 ID를 기반으로 액세스 토큰을 발급
    public TokenDto issueAccessToken(Long userId){

        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);

        // 액세스 토큰 발급 요청
        return tokenHelperIfs.issueAccessToken(data);
    }

    // 사용자 ID를 기반으로 리프레시 토큰을 발급
    public TokenDto issueRefreshToken(Long userId){

        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);

        // 리프레시 토큰 발급 요청
        return tokenHelperIfs.issueRefreshToken(data);
    }

    // 주어진 토큰을 검증하고 사용자 ID를 반환
    public Long validationToken(String token){

        // 토큰 검증
        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);
        Object userId = map.get("userId");

        // 사용자 ID가 null일 경우 ApiException 발생
        Objects.requireNonNull(userId, () ->{throw new ApiException(ErrorCode.NULL_POINT);});

        // 사용자 ID를 Long 타입으로 변환하여 반환
        return Long.parseLong(userId.toString());
    }

}
