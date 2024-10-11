package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    // 현재 진행 중인 주문 건 조회
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(@Parameter(hidden = true) @UserSession User user){

        // 현재 주문 내역 조회
        List<UserOrderDetailResponse> response = userOrderBusiness.current(user);
        return Api.OK(response);
    }

    // 과거 주문 내역
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(@Parameter(hidden = true) @UserSession User user){

        // 과거 주문 내역 조회
        List<UserOrderDetailResponse> response = userOrderBusiness.history(user);
        return Api.OK(response);
    }

    // 특정 주문 1건에 대한 내역 조회
    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> read(@Parameter(hidden = true) @UserSession User user, @PathVariable Long orderId){

        // 특정 주문 내역 조회
        UserOrderDetailResponse response = userOrderBusiness.read(user, orderId);
        return Api.OK(response);
    }

}
