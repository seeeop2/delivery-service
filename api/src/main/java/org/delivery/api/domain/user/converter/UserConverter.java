package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

// 사용자 관련 데이터 전환 로직을 처리하는 Converter 클래스

@RequiredArgsConstructor
@Converter
public class UserConverter {

    // UserRegisterRequest를 UserEntity로 변환하는 메서드
    public UserEntity toEntity(UserRegisterRequest request) {

        // Optional을 사용하여 null 체크 및 처리
        return Optional.ofNullable(request)
                .map(it -> {
                    // UserEntity 객체를 빌더 패턴으로 생성
                    return UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .address(request.getAddress())
                            .build();
                })
                // request가 null일 경우 예외 발생
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"UserRegisterRequest Null"));
    }

    // UserEntity를 UserResponse로 변환하는 메서드
    public UserResponse toResponse(UserEntity userEntity) {

        // Optional을 사용하여 null 체크 및 처리
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    // UserResponse 객체를 빌더 패턴으로 생성
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .status(userEntity.getStatus())
                            .email(userEntity.getEmail())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();
                })
                // userEntity가 null일 경우 예외 발생
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT,"UserEntity Null"));
    }
}
