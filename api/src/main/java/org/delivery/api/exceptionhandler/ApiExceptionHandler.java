package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCodeIfs;
import org.delivery.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// 최우선 처리 (가장 먼저 실행됨)
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

    // ApiException을 처리하는 메서드
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException) {

        // 발생한 ApiException의 로그를 기록함
        log.error("", apiException);

        // 에러 코드 정보를 가져옴
        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();

        // 에러 응답을 생성하여 클라이언트에 반환함
        return ResponseEntity
                // HTTP 상태 코드 설정
                .status(errorCode.getHttpStatusCode())
                // 에러 응답 본문 생성
                .body(Api.ERROR(errorCode, apiException.getErrorDescription()))
                ;
    }
}
