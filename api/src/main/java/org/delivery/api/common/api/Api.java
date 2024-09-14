package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    // 항상 값이 유효한지 검사하도록 설정함.
    @Valid
    private T body;

    public static <T> Api<T> OK(T data){
        Api<T> api = new Api<>();

        api.result = Result.OK();
        api.body = data;

        return api;
    }

}
