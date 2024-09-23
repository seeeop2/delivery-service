package org.delivery.api.domain.token.ifs;

import org.delivery.api.domain.token.model.TokenDto;

import java.util.Map;

// JWT 토큰 발급 및 검증을 위한 인터페이스
public interface TokenHelperIfs {

    // 액세스 토큰을 발급하는 메서드
    TokenDto issueAccessToken(Map<String, Object> data);

    // 리프레시 토큰을 발급하는 메서드
    TokenDto issueRefreshToken(Map<String, Object> data);

    // 주어진 토큰을 검증하는 메서드
    Map<String, Object> validationTokenWithThrow(String token);

}
