package org.delivery.storeadmin.domain.storemenu.converter;

import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreMenuConverter {

    // StoreMenuEntity를 StoreMenuResponse로 변환하는 메서드
    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity) {
        return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId()) // 메뉴 ID 설정
                .name(storeMenuEntity.getName()) // 메뉴 이름 설정
                .status(storeMenuEntity.getStatus()) // 메뉴 상태 설정
                .amount(storeMenuEntity.getAmount()) // 메뉴 가격 설정
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl()) // 썸네일 URL 설정
                .likeCount(storeMenuEntity.getLikeCount()) // 좋아요 수 설정
                .sequence(storeMenuEntity.getSequence()) // 표시 순서 설정
                .build();
    }

    // StoreMenuEntity 리스트를 StoreMenuResponse 리스트로 변환하는 메서드
    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list){
        return list.stream()
                .map(it -> toResponse(it)) // 각 StoreMenuEntity를 StoreMenuResponse로 변환
                .collect(Collectors.toList()); // 변환된 리스트 반환
    }
}
