package org.delivery.storeadmin.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    // 특정 주문 ID에 해당하는 사용자 주문을 조회하는 메서드
    public Optional<UserOrderEntity> getUserOrder(Long id){
        return userOrderRepository.findById(id);
    }

}
