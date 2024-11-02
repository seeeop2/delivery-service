package org.delivery.storeadmin.domain.userorder.converter;

import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class UserOrderConverter {

    // UserOrderEntity를 UserOrderResponse로 변환하는 메서드
    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity) {
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId()) // 주문 ID 설정
                .userId(userOrderEntity.getUserId()) // 사용자 ID 설정
                .storeId(userOrderEntity.getStoreId()) // 스토어 ID 설정
                .status(userOrderEntity.getStatus()) // 주문 상태 설정
                .amount(userOrderEntity.getAmount()) // 주문 금액 설정
                .orderAt(userOrderEntity.getOrderAt()) // 주문 시간 설정
                .acceptedAt(userOrderEntity.getAcceptedAt()) // 주문 확인 시간 설정
                .cookingStartedAt(userOrderEntity.getCookingStartedAt()) // 요리 시작 시간 설정
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt()) // 배달 시작 시간 설정
                .receivedAt(userOrderEntity.getReceivedAt()) // 주문 완료 시간 설정
                .build();
    }
}
