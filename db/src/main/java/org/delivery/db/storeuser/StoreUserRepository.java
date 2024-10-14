package org.delivery.db.storeuser;

import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// StoreUserEntity에 대한 JPA 리포지토리 인터페이스
public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    // 이메일과 상태로 상점 사용자 엔티티를 조회하는 메서드
    // SELECT * FROM STORE_USER WHERE EMAIL = ? AND STATUS = ? ORDER BY ID DESC LIMIT 1
    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);
}
