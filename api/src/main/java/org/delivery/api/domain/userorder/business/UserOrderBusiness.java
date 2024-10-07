package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
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

    private final StoreMenuService storeMenuService;

    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;


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

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }
}
