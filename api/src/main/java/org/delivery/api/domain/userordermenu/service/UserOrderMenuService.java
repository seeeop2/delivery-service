package org.delivery.api.domain.userordermenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // 새로운 주문 메뉴를 등록하는 메서드
    public UserOrderMenuEntity order(UserOrderMenuEntity userOrderMenuEntity){
        // 주문 메뉴 엔티티가 null인지 확인
        return Optional.ofNullable(userOrderMenuEntity)
                .map(it -> {
                    // 상태를 등록으로 설정
                    it.setStatus(UserOrderMenuStatus.REGISTERED);
                    // 주문 메뉴를 저장하고 반환
                    return userOrderMenuRepository.save(it);
                })
                // null일 경우 예외 발생
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
