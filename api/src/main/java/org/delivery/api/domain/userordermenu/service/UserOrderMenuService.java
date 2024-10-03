package org.delivery.api.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;

// 사용자 주문 메뉴 관련 서비스 클래스

@RequiredArgsConstructor
@Service
public class UserOrderMenuService {

    private final UserOrderMenuRepository userOrderMenuRepository;

    // 특정 주문 ID에 해당하는 등록된 메뉴 리스트를 조회하는 메서드
    public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId){

        // 주문 ID와 상태가 등록된 메뉴를 조회하여 반환
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }

}
