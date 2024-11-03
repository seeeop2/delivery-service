package org.delivery.storeadmin.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserOrderMenuService {
    private final UserOrderMenuRepository userOrderMenuRepository;

    // 특정 사용자 주문 ID에 해당하는 등록된 사용자 주문 메뉴 목록을 조회하는 메서드
    public List<UserOrderMenuEntity> getUserOrderMenuList(Long userOrderId){
        // 등록된 메뉴 목록 반환
        return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
    }
}
