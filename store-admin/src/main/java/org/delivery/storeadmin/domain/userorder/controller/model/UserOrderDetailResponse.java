package org.delivery.storeadmin.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

// 사용자 주문 상세 응답 모델 클래스

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse; // 사용자 주문 정보
    private List<StoreMenuResponse> storeMenuResponseList; // 주문에 포함된 메뉴 목록

}
