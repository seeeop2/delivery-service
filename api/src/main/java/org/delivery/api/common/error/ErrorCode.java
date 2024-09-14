package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{
    OK(200,200,"성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),400, "잘못된 요청" ),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512,"Null point"),
    ;

    // 변형이 일어나면 안되기 때문에, final 처리함.
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
