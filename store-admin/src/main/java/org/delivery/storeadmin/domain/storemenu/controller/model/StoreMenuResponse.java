package org.delivery.storeadmin.domain.storemenu.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storemenu.enums.StoreMenuStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreMenuResponse {

    private Long id; // 메뉴 ID

    private String name; // 메뉴 이름

    private BigDecimal amount; // 메뉴 가격

    private StoreMenuStatus status; // 메뉴 상태 (열거형으로 관리)

    private String thumbnailUrl; // 메뉴 썸네일 이미지 URL

    private int likeCount; // 좋아요 수

    private int sequence; // 메뉴의 표시 순서

}
