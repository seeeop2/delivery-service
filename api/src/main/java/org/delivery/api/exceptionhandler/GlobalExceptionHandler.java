package org.delivery.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 예외 처리기를 정의하는 클래스임

@Slf4j
// 가장 마지막에 실행 적용(파라미터 숫자 높을수록 후순위)
@Order(value = Integer.MAX_VALUE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외를 처리하는 메서드임
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(Exception exception){
        // 예외 발생 시 로그에 에러 메시지를 기록함
        log.error("", exception);

        // 서버 에러 응답을 반환함
        return ResponseEntity
                // HTTP 상태 코드 500 (서버 오류)
                .status(500)
                // 서버 에러를 나타내는 Api 객체 생성
                .body(Api.ERROR(ErrorCode.SERVER_ERROR))
                ;
    }
}
