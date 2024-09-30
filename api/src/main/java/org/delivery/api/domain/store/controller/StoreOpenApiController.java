package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/open-api/store")
@RestController
public class StoreOpenApiController {

    private final StoreBusiness storeBusiness;

    // 스토어 등록 API 엔드포인트
    @PostMapping("/register")
    public Api<StoreResponse> register(@Valid @RequestBody Api<StoreRegisterRequest> request) {

        // 요청 본문에서 StoreRegisterRequest를 추출하고 유효성 검사 수행
        StoreResponse response = storeBusiness.register(request.getBody());
        // 성공적인 응답을 Api 형태로 반환
        return Api.OK(response);
    }

}
