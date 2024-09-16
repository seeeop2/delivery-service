package org.delivery.api.common.exception;

import org.delivery.api.common.error.ErrorCodeIfs;

// API 예외 처리를 위한 인터페이스
public interface ApiExceptionIfs {

    // 에러 코드 정보를 반환하는 메서드
    ErrorCodeIfs getErrorCodeIfs();

    // 에러 설명을 반환하는 메서드
    String getErrorDescription();
}
