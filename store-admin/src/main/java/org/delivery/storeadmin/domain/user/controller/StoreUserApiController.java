package org.delivery.storeadmin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/store-user")
@RestController
public class StoreUserApiController {

    private final StoreUserConverter storeUserConverter;

    // 현재 인증된 사용자 정보를 반환하는 메서드
    @GetMapping("/me")
    public StoreUserResponse me(@AuthenticationPrincipal @Parameter(hidden = true) UserSession userSession){

        return storeUserConverter.toResponse(userSession);
    }

}
