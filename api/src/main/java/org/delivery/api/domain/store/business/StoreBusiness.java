package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    // 스토어 등록 메서드
    public StoreResponse register(StoreRegisterRequest storeRegisterRequest){

        // request -> entity -> response 변환 과정 수행

        // StoreRegisterRequest를 StoreEntity로 변환
        StoreEntity entity = storeConverter.toEntity(storeRegisterRequest);
        // 스토어 등록
        StoreEntity newEntity = storeService.register(entity);
        // 등록된 StoreEntity를 StoreResponse로 변환
        StoreResponse response = storeConverter.toResponse(newEntity);

        // 변환된 응답 반환
        return response;
    }

    // 카테고리로 스토어 검색 메서드
    public List<StoreResponse> searchCategory(StoreCategory storeCategory){

        // entity list -> response list 변환 과정 수행

        // 주어진 카테고리로 스토어 리스트 조회
        List<StoreEntity> storeList = storeService.searchByCategory(storeCategory);

        // StoreEntity 리스트를 StoreResponse 리스트로 변환
        return storeList.stream()
                // 각 StoreEntity를 StoreResponse로 변환
                .map(storeConverter::toResponse)
                // 리스트로 수집하여 반환
                .collect(Collectors.toList());
    }
    
}
