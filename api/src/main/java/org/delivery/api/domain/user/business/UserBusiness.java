package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.db.user.UserEntity;

// 사용자 관련 비즈니스 로직을 처리하는 Business 클래스

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    // 사용자에 대한 가입 처리 로직
    // 1. request -> entity
    // 2. entity -> save
    // 3. save Entity -> response
    // 4. response return
    public UserResponse register(UserRegisterRequest request) {

        /* Lamda 이용 예시
        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request null"));
        */

        // 모두가 알아볼 수 있는 방법 선호(람다 보다 조금 더 명시적임)
        // UserRegisterRequest를 UserEntity로 변환
        UserEntity entity = userConverter.toEntity(request);
        // 변환된 UserEntity를 저장하고 새로 생성된 엔티티 반환
        UserEntity newEntity = userService.register(entity);
        // 저장된 UserEntity를 UserResponse로 변환
        UserResponse response = userConverter.toResponse(newEntity);

        return response;
    }

    // 1. email, password 를 가지고 사용자를 체크
    // 2. user entity 로그인 확인
    // 3. token 생성
    // 4. token response
    public TokenResponse login(UserLoginRequest request) {

        UserEntity userEntity = userService.login(request.getEmail(), request.getPassword());
        TokenResponse tokenResponse = tokenBusiness.issueToken(userEntity);

        return tokenResponse;
    }

    // 사용자 정보 조회 메서드
    public UserResponse me(User user) {

        // 사용자 ID로 사용자 정보 조회
        UserEntity userEntity = userService.getUserWithThrow(user.getId());

        // 사용자 정보를 UserResponse로 변환
        UserResponse response = userConverter.toResponse(userEntity);

        return response;
    }

}
