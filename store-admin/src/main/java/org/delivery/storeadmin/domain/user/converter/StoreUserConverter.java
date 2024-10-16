package org.delivery.storeadmin.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.common.annotation.Converter;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;

@RequiredArgsConstructor
@Converter
public class StoreUserConverter {

    public StoreUserEntity toEntity(StoreUserRegisterRequest request, StoreEntity storeEntity) {

        return StoreUserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                // TODO: NULL 일 때, 에러 체크 확인 필요
                .storeId(storeEntity.getId())
                .build();
    }

    public StoreUserResponse toResponse(StoreUserEntity storeUserEntity, StoreEntity storeEntity) {
        return StoreUserResponse.builder()
                .user(
                        StoreUserResponse.UserResponse.builder()
                            .id(storeUserEntity.getId())
                            .email(storeUserEntity.getEmail())
                            .status(storeUserEntity.getStatus())
                            .role(storeUserEntity.getRole())
                            .registeredAt(storeUserEntity.getRegisteredAt())
                            .unRegisteredAt(storeUserEntity.getUnRegisteredAt())
                            .lastLoginAt(storeUserEntity.getLastLoginAt())
                            .build()
                )
                .store(
                        StoreUserResponse.StoreResponse.builder()
                            .id(storeEntity.getId())
                            .name(storeEntity.getName())
                            .build()
                )
                .build();
    }

}
