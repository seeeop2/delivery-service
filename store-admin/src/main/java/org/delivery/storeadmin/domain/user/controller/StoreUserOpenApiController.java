package org.delivery.storeadmin.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.user.business.StoreUserBusiness;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/open-api/store-user")
@RestController
public class StoreUserOpenApiController {

    private final StoreUserBusiness storeUserBusiness;

    // 스토어 사용자 등록 API 엔드포인트
    @PostMapping("")
    public StoreUserResponse register(@Valid @RequestBody StoreUserRegisterRequest request){
        // 요청을 기반으로 사용자 등록 처리
        StoreUserResponse response = storeUserBusiness.register(request);

        return response;
    }

}
