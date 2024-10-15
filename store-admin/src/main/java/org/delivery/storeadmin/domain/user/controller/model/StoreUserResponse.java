package org.delivery.storeadmin.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreUserResponse {

    private UserResponse user;

    private StoreResponse store;


    // 사용자 정보에 대한 내부 클래스
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResponse{

        // 사용자 ID
        private Long id;

        // 사용자 이메일
        private String email;

        // 사용자 상태
        private StoreUserStatus status;

        // 사용자 역할
        private StoreUserRole role;

        // 등록 시간
        private LocalDateTime registeredAt;

        // 해지 시간
        private LocalDateTime unRegisteredAt;

        // 마지막 로그인 시간
        private LocalDateTime lastLoginAt;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StoreResponse{

        // 스토어 ID
        private Long id;

        // 스토어 이름
        private String name;

    }

}
