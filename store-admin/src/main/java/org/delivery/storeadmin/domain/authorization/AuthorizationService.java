package org.delivery.storeadmin.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;

    // 사용자 이름으로 UserDetails 를 로드하는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자 이름을 기반으로 등록된 사용자 엔티티 조회
        Optional<StoreUserEntity> storeUserEntity = storeUserService.getRegisterUser(username);

        // 사용자 엔티티가 존재하면 UserDetails 객체 생성
        return storeUserEntity.map(it -> {

            UserDetails user = User.builder()
                    .username(it.getEmail())
                    .password(it.getPassword())
                    .roles(it.getRole().toString())
                    .build();
            return user;

        }).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
