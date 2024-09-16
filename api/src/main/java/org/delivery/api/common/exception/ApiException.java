package org.delivery.api.common.exception;

import lombok.Getter;
import org.delivery.api.common.error.ErrorCodeIfs;

// API 예외를 나타내는 클래스
@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {

    // 에러 코드
    private final ErrorCodeIfs errorCodeIfs;
    // 에러 설명
    private final String errorDescription;

    // 에러 코드만을 받아서 ApiException 생성
    public ApiException(ErrorCodeIfs errorCodeIfs) {
        // 부모 클래스에 에러 설명 전달
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    // 에러 코드와 사용자 정의 에러 설명을 받아서 ApiException 생성
    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        // 부모 클래스에 사용자 정의 에러 설명 전달
        super(errorDescription);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    // 에러 코드와 Throwable을 받아서 ApiException 생성
    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        // 부모 클래스에 Throwable 전달
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    // 에러 코드, Throwable, 사용자 정의 에러 설명을 받아서 ApiException 생성
    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx, String errorDescription) {
        // 부모 클래스에 Throwable 전달
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        // 사용자 정의 에러 설명 저장
        this.errorDescription = errorDescription;
    }
}
