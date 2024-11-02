package org.delivery.storeadmin.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.userorder.enums.UserOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderResponse {

    private Long id; // 주문 ID

    private Long storeId; // 스토어 ID (주문한 가게의 고유 식별자)

    private Long userId; // 사용자 ID (주문한 사용자의 고유 식별자)

    private UserOrderStatus status; // 주문 상태 (열거형으로 관리)

    private BigDecimal amount; // 주문 금액

    private LocalDateTime orderAt; // 주문 시간

    private LocalDateTime acceptedAt; // 주문 확인 시간

    private LocalDateTime cookingStartedAt; // 요리 시작 시간

    private LocalDateTime deliveryStartedAt; // 배달 시작 시간

    private LocalDateTime receivedAt; // 주문 완료 시간

}
