package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

    // 특정 유저의 모든 주문
    // SELECT * FROM USER_ORDER WHERE USER_ID = ? AND STATUS = ? ORDER BY ID DESC
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    // 특정 유저의 여러 상태의 모든 주문을 조회 (내림차순 정렬)
    // SELECT * FROM USER_ORDER WHERE USER_ID = ? AND STATUS IN (?, ? ..) ORDER BY ID DESC
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

    // 특정 주문
    // SELECT * FROM USER_ORDER WHERE ID = ? AND STATUS = ? AND USER_ID = ?
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

}
