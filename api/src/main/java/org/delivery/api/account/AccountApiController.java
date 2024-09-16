package org.delivery.api.account;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountRepository accountRepository;

    @GetMapping("/me")
    public Api<AccountMeResponse> me(){

        AccountMeResponse response =
                AccountMeResponse.builder()
                        .name("홍길동")
                        .email("A@gmail.com")
                        .registeredAt(LocalDateTime.now())
                        .build();

        // 예외 테스트
        String str = "안녕하세요";
        // 예외가 일어날 것인데, 예외는 ExceptionHandler가 캐치할 것임.
        // 컨트롤러에서는 API 호출 성공에 대해서만 작성하며, 예외 처리와 분리를 함.
        int age = Integer.parseInt(str);

        return Api.OK(response);
    }
}
