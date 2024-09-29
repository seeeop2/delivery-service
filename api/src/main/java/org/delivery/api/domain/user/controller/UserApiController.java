package org.delivery.api.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserApiController {

    private final UserBusiness userBusiness;

    // 현재 사용자의 정보를 조회하는 API 엔드포인트
    @GetMapping("/me")
    public Api<UserResponse> me(@UserSession User user) {

        // UserSession 어노테이션을 통해 주입된 사용자 정보를 사용하여 비즈니스 로직 호출
        UserResponse response = userBusiness.me(user);

        // 성공적인 응답을 Api 형식으로 반환
        return Api.OK(response);
    }

}
