package org.delivery.api.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class StoreService {

    private final StoreRepository storeRepository;

    // 유효한 스토어 가져오기
    public StoreEntity getStoreWithThrow(Long id){

        // 주어진 ID와 등록 상태에 해당하는 스토어를 조회하고, 없으면 예외를 던짐
        Optional<StoreEntity> entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity){

        // null 체크 후 스토어의 초기 상태와 평점을 설정하고 저장
        return Optional.ofNullable(storeEntity)
                .map(it -> {
                    // 초기 평점 설정
                    it.setStar(0);
                    // 상태를 등록으로 설정
                    it.setStatus(StoreStatus.REGISTERED);

                    // TODO 등록일시 추가하기

                    // 스토어 저장
                    return storeRepository.save(it);
                })
                // null인 경우 예외 던짐
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 카테고리로 스토어 검색
    public List<StoreEntity> searchByCategory(StoreCategory storeCategory){
        // 주어진 카테고리와 등록 상태에 해당하는 스토어 리스트를 조회
        List<StoreEntity> list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(StoreStatus.REGISTERED, storeCategory);
        return list;
    }

    // 전체 스토어
    public List<StoreEntity> registerStore(){
        // 등록 상태의 모든 스토어를 조회
        List<StoreEntity> list = storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
        return list;
    }
}
