package org.delivery.db.userordermenu;

import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

    // 주문 아이디에 해당하는 등록된 모든 메뉴 리스트 조회
    // SELECT * FROM USER_ORDER_MENU WHERE USER_ORDER_ID = ? AND STATUS = ?
    List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId, UserOrderMenuStatus status);

}
