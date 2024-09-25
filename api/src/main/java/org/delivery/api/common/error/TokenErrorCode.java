package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 토큰 관련 에러 코드를 정의하는 열거형(Enum) 클래스
// Token의 경우, 2000번대 에러코드를 사용함.

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs {

    // 유효하지 않은 토큰 에러 코드
    INVALID_TOKEN(400, 2000, "유효하지 않은 토큰"),

    // 만료된 토큰 에러 코드
    EXPIRED_TOKEN(400, 2001, "만료된 토큰"),

    // 알 수 없는 토큰 에러 코드
    TOKEN_EXCEPTION(400, 2002, "토큰 알 수 없는 에러"),

    // 인증 헤더에 토큰이 없는 경우 에러 코드
    AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "인증 헤더 토큰 없음");

    // HTTP 상태 코드
    private final Integer httpStatusCode;
    // 사용자 정의 에러 코드
    private final Integer errorCode;
    // 에러 설명
    private final String description;

}
