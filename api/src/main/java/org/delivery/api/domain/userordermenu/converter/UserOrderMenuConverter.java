package org.delivery.api.domain.userordermenu.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

@Converter
public class UserOrderMenuConverter {

    // UserOrderEntity와 StoreMenuEntity를 기반으로 UserOrderMenuEntity를 생성하는 메서드
    public UserOrderMenuEntity toEntity(UserOrderEntity userOrderEntity, StoreMenuEntity storeMenuEntity){

        return UserOrderMenuEntity.builder()
                // 주문 ID 설정
                .userOrderId(userOrderEntity.getId())
                // 스토어 메뉴 ID 설정
                .storeMenuId(storeMenuEntity.getId())
                .build();
    }
}
