package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderProducer userOrderProducer;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    // 사용자와 주문 요청을 받아 주문을 처리하는 메서드
    // 1. 사용자, 메뉴 ID
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        // 요청에서 메뉴 ID 리스트를 가져와서 해당 메뉴 엔티티 리스트를 생성
        List<StoreMenuEntity> storeMenuEnityList = body.getStoreMenuIdList()
                .stream()
                // 각 메뉴 ID로부터 스토어 메뉴 엔티티를 가져옴
                .map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());

        // 사용자와 메뉴 리스트를 기반으로 UserOrderEntity 생성
        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeMenuEnityList);

        // 주문 생성
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 주문 메뉴 엔티티 리스트 생성 (맵핑)
        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEnityList
                .stream()
                .map(it -> {
                    // 스토어 메뉴와 사용자 주문을 기반으로 UserOrderMenuEntity 생성
                    UserOrderMenuEntity userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                    return userOrderMenuEntity;
                })
                .collect(Collectors.toList());

        // 주문 메뉴 엔티티를 데이터베이스에 저장
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    // 현재 진행 중인 주문 내역 조회
    public List<UserOrderDetailResponse> current(User user) {

        List<UserOrderEntity> userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리
        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            // 사용자가 주문한 메뉴
            List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            }).collect(Collectors.toList());

            // 사용자가 주문한 스토어
            // TODO 리팩토링 필요
            StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    // 주문 응답 변환
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    // 메뉴 응답 변환
                    .storeMenuResponsesList(storeMenuConverter.toResponses(storeMenuEntityList))
                    // 스토어 응답 변환
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).collect(Collectors.toList());

        // 주문 상세 응답 리스트 반환
        return userOrderDetailResponseList;
    }

    // 과거 주문 내역 조회
    public List<UserOrderDetailResponse> history(User user) {

        List<UserOrderEntity> userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리
        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            // 사용자가 주문한 메뉴
            List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
                StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            }).collect(Collectors.toList());

            // 사용자가 주문한 스토어
            // TODO 리팩토링 필요
            StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    // 주문 응답 변환
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    // 메뉴 응답 변환
                    .storeMenuResponsesList(storeMenuConverter.toResponses(storeMenuEntityList))
                    // 스토어 응답 변환
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).collect(Collectors.toList());

        // 주문 상세 응답 리스트 반환
        return userOrderDetailResponseList;

    }

    // 특정 주문 조회
    public UserOrderDetailResponse read(User user, Long orderId) {

        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());

        // 사용자가 주문한 메뉴
        List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream().map(userOrderMenuEntity -> {
            StoreMenuEntity storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            return storeMenuEntity;
        }).collect(Collectors.toList());

        // 사용자가 주문한 스토어
        // TODO 리팩토링 필요
        StoreEntity storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                // 주문 응답 변환
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                // 주문 응답 변환
                .storeMenuResponsesList(storeMenuConverter.toResponses(storeMenuEntityList))
                // 스토어 응답 변환
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
