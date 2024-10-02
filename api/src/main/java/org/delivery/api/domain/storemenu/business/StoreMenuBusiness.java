package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    // 메뉴 등록 처리
    public StoreMenuResponse register(StoreMenuRegisterRequest request){

        // 요청 -> 엔티티로 변환 -> 저장 -> 응답 생성

        // 요청을 엔티티로 변환
        StoreMenuEntity entity = storeMenuConverter.toEntity(request);
        // 엔티티 저장
        StoreMenuEntity newEntity = storeMenuService.register(entity);
        // 저장된 엔티티를 응답으로 변환
        StoreMenuResponse response = storeMenuConverter.toResponse(newEntity);

        // 응답 반환
        return response;
    }

    // 특정 스토어의 메뉴 검색
    public List<StoreMenuResponse> search(Long storeId){
        // 스토어 ID로 메뉴 목록 조회
        List<StoreMenuEntity> list = storeMenuService.getStoreMenuByStoreId(storeId);

        return list.stream()
                // 엔티티 목록을 응답으로 변환
                .map(storeMenuConverter::toResponse)
                // 리스트로 수집하여 반환
                .collect(Collectors.toList());
    }

}
