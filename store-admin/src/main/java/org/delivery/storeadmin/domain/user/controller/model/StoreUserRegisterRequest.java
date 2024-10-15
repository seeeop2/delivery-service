package org.delivery.storeadmin.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserRegisterRequest {

    // 스토어 이름
    @NotBlank
    private String storeName;

    // 사용자 이메일
    @NotBlank
    private String email;

    // 사용자 비밀번호
    @NotBlank
    private String password;

    // 사용자 역할
    @NotBlank
    private StoreUserRole role;

}
