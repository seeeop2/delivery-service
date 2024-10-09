package org.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    // 사용자 주문 응답 모델
    private UserOrderResponse userOrderResponse;

    // 스토어 응답 모델
    private StoreResponse storeResponse;

    // 스토어 메뉴 응답 모델 리스트
    private List<StoreMenuResponse> storeMenuResponsesList;

}
