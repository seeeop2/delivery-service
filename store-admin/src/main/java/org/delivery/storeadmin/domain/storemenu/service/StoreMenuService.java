package org.delivery.storeadmin.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    // 특정 ID에 해당하는 스토어 메뉴를 조회하고, 메뉴 상태가 REGISTERED인 경우에만 반환하는 메서드
    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED)
                .orElseThrow(() -> new RuntimeException("Store menu not found")); // 메뉴가 없을 경우 예외 발생
    }

}
