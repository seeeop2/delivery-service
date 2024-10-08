package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/user-order")
@RestController
public class UserOrderApiController {
    private final UserOrderBusiness userOrderBusiness;

    // 사용자 주문 처리 메서드
    @PostMapping("")
    public Api<UserOrderResponse> userOrder(@Valid @RequestBody Api<UserOrderRequest> userOrderRequest, @Parameter(hidden = true) @UserSession User user) {

        // 사용자와 유효성 검사를 통과한 주문 요청을 기반으로 주문 처리
        UserOrderResponse response = userOrderBusiness.userOrder(user, userOrderRequest.getBody());

        // 성공적인 응답 반환
        return Api.OK(response);
    }

}
