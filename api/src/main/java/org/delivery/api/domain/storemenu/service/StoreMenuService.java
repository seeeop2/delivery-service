package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    // 특정 ID의 등록된 메뉴를 조회하고, 없을 경우 예외 발생
    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        Optional<StoreMenuEntity> entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED);

        // 메뉴가 없으면 ApiException 발생
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 특정 가게의 등록된 모든 메뉴를 조회
    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED);
    }

    // 스토어 메뉴 등록
    public StoreMenuEntity register(StoreMenuEntity storeMenuEntity){
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> {
                    // 메뉴의 상태 설정
                    it.setStatus(StoreMenuStatus.REGISTERED);
                    // 메뉴 저장
                    return storeMenuRepository.save(it);
                })
                // 메뉴가 null일 경우 ApiException 발생
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
