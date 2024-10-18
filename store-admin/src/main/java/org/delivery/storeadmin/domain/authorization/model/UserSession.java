package org.delivery.storeadmin.domain.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession implements UserDetails {

    // 사용자 ID
    private Long userId;
    // 사용자 이메일
    private String email;
    // 사용자 비밀번호
    private String password;
    // 사용자 상태 (등록, 비활성화 등)
    private StoreUserStatus status;
    // 사용자 역할 (관리자, 일반 사용자 등)
    private StoreUserRole role;
    // 등록 일시
    private LocalDateTime registeredAt;
    // 탈퇴 일시
    private LocalDateTime unRegisteredAt;
    // 마지막 로그인 일시
    private LocalDateTime lastLoginAt;

    // 스토어 ID
    private Long storeId;
    // 스토어 이름
    private String storeName;

    // 권한 정보를 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 역할을 권한으로 변환하여 반환
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    // 비밀번호를 반환하는 메서드
    @Override
    public String getPassword() {
        return this.password;
    }

    // 사용자 이름을 반환하는 메서드
    @Override
    public String getUsername() {
        return this.email;
    }

    // 계정이 만료되지 않았는지 확인하는 메서드
    @Override
    public boolean isAccountNonExpired() {
        return this.status == StoreUserStatus.REGISTERED;
    }

    // 계정이 잠기지 않았는지 확인하는 메서드
    @Override
    public boolean isAccountNonLocked() {
        return this.status == StoreUserStatus.REGISTERED;
    }

    // 자격 증명이 만료되지 않았는지 확인하는 메서드
    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == StoreUserStatus.REGISTERED;
    }

    // 계정이 활성화되었는지 확인하는 메서드
    @Override
    public boolean isEnabled() {
        // 항상 활성화된 상태로 반환
        return true;
    }
}
