package org.delivery.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    // TODO: SERVICE 로 변경하기
    private final StoreRepository storeRepository;

    // 사용자 등록 메서드
    public StoreUserResponse register(StoreUserRegisterRequest request){

        // 주어진 스토어 이름과 상태로 스토어 엔티티 조회
        Optional<StoreEntity> storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);
        // 요청을 기반으로 StoreUserEntity 생성
        StoreUserEntity entity = storeUserConverter.toEntity(request, storeEntity.get());
        // 사용자 등록 및 새로운 사용자 엔티티 반환
        StoreUserEntity newEntity = storeUserService.register(entity);
        // 새로운 사용자 엔티티와 스토어 엔티티를 기반으로 응답 모델 생성
        StoreUserResponse response = storeUserConverter.toResponse(newEntity, storeEntity.get());

        return response;
    }

}
