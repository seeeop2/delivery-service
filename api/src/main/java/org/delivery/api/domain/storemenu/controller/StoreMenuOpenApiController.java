package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/open-api/store-menu")
@RestController
public class StoreMenuOpenApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    // 메뉴 등록 엔드포인트
    @PostMapping("/register")
    public Api<StoreMenuResponse> register(@Valid @RequestBody Api<StoreMenuRegisterRequest> request){
        // 요청 본문에서 데이터 추출
        StoreMenuRegisterRequest req = request.getBody();
        // 비즈니스 로직을 통해 메뉴 등록 처리
        StoreMenuResponse response = storeMenuBusiness.register(req);

        // 성공 응답 반환
        return Api.OK(response);
    }
}
