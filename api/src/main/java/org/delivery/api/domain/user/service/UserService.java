package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

// User 도메인 로직을 처리하는 서비스

@RequiredArgsConstructor
@Service
public class UserService {

    // UserRepository 의존성 주입
    private final UserRepository userRepository;

    // 사용자 등록 메서드
    public UserEntity register(UserEntity userEntity){

        // Optional을 사용하여 null 체크 및 처리
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    // 사용자 상태를 등록 상태로 설정
                    userEntity.setStatus(UserStatus.REGISTERED);
                    // 현재 시간으로 등록 일시 설정
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    // 사용자 정보를 저장하고 반환
                    return userRepository.save(userEntity);
                })
                // userEntity가 null일 경우 예외 발생
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }

}
