package org.delivery.api.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/store")
@RestController
public class StoreApiController {

    private final StoreBusiness storeBusiness;

    // 스토어 검색 API 엔드포인트
    @GetMapping("/search")
    public Api<List<StoreResponse>> search(@RequestParam(required = false) StoreCategory storeCategory){
        // 주어진 카테고리로 스토어 리스트를 검색
        List<StoreResponse> response = storeBusiness.searchCategory(storeCategory);
        // 성공적인 응답을 Api 형태로 반환
        return Api.OK(response);
    }

}
