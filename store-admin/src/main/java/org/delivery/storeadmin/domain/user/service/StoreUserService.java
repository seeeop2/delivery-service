package org.delivery.storeadmin.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    // 스토어 사용자 등록 메서드
    public StoreUserEntity register(StoreUserEntity storeUserEntity){
        // 사용자 상태를 등록으로 설정
        storeUserEntity.setStatus(StoreUserStatus.REGISTERED);
        // 비밀번호 암호화
        storeUserEntity.setPassword(passwordEncoder.encode(storeUserEntity.getPassword()));
        // 등록 시간 설정
        storeUserEntity.setRegisteredAt(LocalDateTime.now());

        return storeUserRepository.save(storeUserEntity);
    }

    // 등록된 사용자 조회 메서드
    public Optional<StoreUserEntity> getRegisterUser(String email){
        // 이메일과 상태로 사용자 조회
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }

}
