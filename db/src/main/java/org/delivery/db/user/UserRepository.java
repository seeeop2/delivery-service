package org.delivery.db.user;

import org.delivery.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // SELECT * FROM USER WHERE ID = ? AND STATUS = ? ORDER BY ID DESC LIMIT 1;
    Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, UserStatus status);

    // SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ? AND STATUS = ? ORDER BY ID DESC LIMIT 1;
    Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus status);
}
