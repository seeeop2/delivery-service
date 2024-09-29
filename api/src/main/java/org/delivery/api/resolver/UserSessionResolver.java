package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.delivery.db.user.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// request 요청이 들어오면 실행됨.

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    // 지원하는 파라미터 체크, 어노테이션 체크
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // 1. 메서드 파라미터에 UserSession 어노테이션이 있는지 체크
        boolean annotation = parameter.hasParameterAnnotation(UserSession.class);

        // 2. 파라미터의 타입이 User인지 체크
        boolean parameterType = parameter.getParameterType().equals(User.class);

        // 두 조건이 모두 만족할 경우 true 반환
        return (annotation && parameterType);
    }

    // supportsParameter에서 true를 반환할 경우 실행됨
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 현재 요청의 속성에서 사용자 ID를 가져옴
        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        // 사용자 ID를 기반으로 UserEntity를 가져옴
        UserEntity userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

        // UserEntity 정보를 기반으로 User 객체를 생성하여 반환
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .password(userEntity.getPassword())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt() )
                .unregisteredAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();
    }

}
