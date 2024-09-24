package org.delivery.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/open-api/user")
@RestController
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    // 사용자 가입 요청 처리
    // 파라미터에 @Valid 어노테이션을 사용하여 유효성 검사를 수행함
    // 즉, 해당 파라미터의 클래스에 적용된 validation 을 모두 체크함.
    @PostMapping("/register")
    public Api<UserResponse> register(@Valid @RequestBody Api<UserRegisterRequest> request){

        // 요청 본문에서 UserRegisterRequest를 추출하여 사용자 비즈니스 로직에 전달
        UserResponse response = userBusiness.register(request.getBody());
        // 성공적인 응답을 Api 형식으로 반환
        return Api.OK(response);

    }

    // 로그인
    @PostMapping("/login")
    public Api<TokenResponse> login(@Valid @RequestBody Api<UserLoginRequest> request){

        TokenResponse response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }

}
