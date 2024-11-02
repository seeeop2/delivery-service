package org.delivery.db.userorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.userorder.enums.UserOrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "user_order")
@Entity
public class UserOrderEntity extends BaseEntity {

    // 사용자 ID (1:N 관계)
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeId;

    // 주문 상태 (열거형)
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private UserOrderStatus status;

    // 주문 금액
    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal amount;

    // 주문 시간
    private LocalDateTime orderAt;

    // 주문 확인 시간
    private LocalDateTime acceptedAt;

    // 요리 시작 시간
    private LocalDateTime cookingStartedAt;

    // 배달 시작 시간
    private LocalDateTime deliveryStartedAt;

    // 주문 완료 시간
    private LocalDateTime receivedAt;

}
