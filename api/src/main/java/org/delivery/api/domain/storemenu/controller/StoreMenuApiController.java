package org.delivery.api.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/store-menu")
@RestController
public class StoreMenuApiController {

    private final StoreMenuBusiness storeMenuBusiness;

    // 특정 스토어의 메뉴 검색 엔드포인트
    public Api<List<StoreMenuResponse>> search(@RequestParam Long storeId){
        // 스토어 ID로 메뉴 목록 조회
        List<StoreMenuResponse> response = storeMenuBusiness.search(storeId);
        // 성공 응답 반환
        return Api.OK(response);
    }
}
