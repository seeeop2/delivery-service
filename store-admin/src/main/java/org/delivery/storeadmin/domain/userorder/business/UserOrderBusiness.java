package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderMenuService userOrderMenuService;
    private final UserOrderConverter userOrderConverter;

    private final SseConnectionPool sseConnectionPool;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    // 프로세스:
    // 주문
    // 주문 내역 찾기
    // 스토어 찾기
    // 연결된 세션 찾아서
    // PUSH

    // 사용자 주문 정보를 클라이언트에 PUSH하는 메서드
    public void pushUserOrder(UserOrderMessage userOrderMessage) {

        // 사용자 주문 ID를 기반으로 주문 내역 조회
        UserOrderEntity userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId()).orElseThrow(
                () -> new RuntimeException("사용자 주문내역 없음.")
        );

        // 사용자 주문 메뉴 목록 조회
        List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // 사용자 주문 메뉴를 스토어 메뉴로 변환
        List<StoreMenuResponse> storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> {
                    return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()); // 스토어 메뉴 엔티티 조회
                })
                .map(storeMenuEntity -> {
                    return storeMenuConverter.toResponse(storeMenuEntity); // 스토어 메뉴 엔티티를 응답 모델로 변환
                })
                .collect(Collectors.toList());

        UserOrderResponse userOrderResponse = userOrderConverter.toResponse(userOrderEntity); // 사용자 주문 응답 모델 생성

        // 사용자 주문 상세 응답 모델 생성
        UserOrderDetailResponse push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();

        // 연결된 SSE 세션을 찾음
        UserSseConnection userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 PUSH
        userConnection.sendMessage(push); // PUSH 메시지 전송
    }

}
