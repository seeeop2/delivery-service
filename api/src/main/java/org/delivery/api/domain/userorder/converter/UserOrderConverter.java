package org.delivery.api.domain.userorder.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {

    // 사용자와 스토어 메뉴 리스트를 기반으로 UserOrderEntity를 생성하는 메서드
    public UserOrderEntity toEntity(User user, Long storeId, List<StoreMenuEntity> storeMenuEntityList){

        // 스토어 메뉴 리스트의 금액을 합산하여 총 금액 계산
        BigDecimal totalAmount = storeMenuEntityList.stream()
                // 각 스토어 메뉴의 금액을 추출
                .map(it -> it.getAmount())
                // 금액을 합산
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // UserOrderEntity 빌더를 사용하여 객체 생성
        return UserOrderEntity.builder()
                .userId(user.getId())
                .storeId(storeId)
                .amount(totalAmount) // 총 금액 설정
                .build();
    }

    // UserOrderEntity를 UserOrderResponse로 변환하는 메서드
    public UserOrderResponse toResponse(UserOrderEntity userOrderEntity){
        return UserOrderResponse.builder()
                .id(userOrderEntity.getId())
                .status(userOrderEntity.getStatus())
                .amount(userOrderEntity.getAmount())
                .orderAt(userOrderEntity.getOrderAt())
                .acceptedAt(userOrderEntity.getAcceptedAt())
                .cookingStartedAt(userOrderEntity.getCookingStartedAt())
                .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
                .receivedAt(userOrderEntity.getReceivedAt())
                .build();
    }

}
