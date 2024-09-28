package org.delivery.api.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserApiController {

    private final UserBusiness userBusiness;

    // 현재 사용자의 정보를 조회하는 API 엔드포인트
    @GetMapping("/me")
    public Api<UserResponse> me() {

        // 현재 사용자 ID를 null로 전달하여 사용자 정보를 조회 (추후 사용자 ID를 구현해야 함)
        UserResponse response = userBusiness.me();

        // 성공적인 응답을 Api 형식으로 반환
        return Api.OK(response);
    }

}
