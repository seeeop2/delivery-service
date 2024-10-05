package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;

    // 특정 주문 ID와 사용자 ID에 해당하는 등록된 주문을 조회 (없으면 예외 발생)
    public UserOrderEntity getUserOrderWithThrow(Long id, Long userId){
        return userOrderRepository.findAllByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 특정 사용자 ID에 해당하는 등록된 주문 리스트 조회
    public List<UserOrderEntity> getUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }

    // 특정 사용자 ID에 해당하는 여러 상태의 주문 리스트 조회
    public List<UserOrderEntity> getUserOrderList(Long userId, List<UserOrderStatus> statusList){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statusList);
    }

    // 현재 진행 중인 주문 내역 조회
    public List<UserOrderEntity> current(Long userId){
        return getUserOrderList(userId,
                List.of(
                    UserOrderStatus.ORDER,
                    UserOrderStatus.COOKING,
                    UserOrderStatus.DELIVERY,
                    UserOrderStatus.ACCEPT
                )
        );
    }

    // 과거 주문한 내역
    public List<UserOrderEntity> history(Long userId){
        return getUserOrderList(userId,
                List.of(
                        UserOrderStatus.RECEIVE
                )
        );
    }

    // 주문 (CREATE)
    public UserOrderEntity order(UserOrderEntity userOrderEntity){
        return Optional.ofNullable(userOrderEntity)
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderAt(LocalDateTime.now());
                    return userOrderRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 주문 상태 변경
    public UserOrderEntity setStatus(UserOrderEntity userorderEntity, UserOrderStatus status){
        userorderEntity.setStatus(status);
        return userOrderRepository.save(userorderEntity);
    }

    // 주문 확인 (상태 변경)
    public UserOrderEntity accept(UserOrderEntity userorderEntity){
        userorderEntity.setAcceptedAt(LocalDateTime.now());
        return setStatus(userorderEntity, UserOrderStatus.ACCEPT);
    }

    // 조리 시작 (상태 변경)
    public UserOrderEntity cooking(UserOrderEntity userorderEntity){
        userorderEntity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(userorderEntity, UserOrderStatus.COOKING);
    }

    // 배달 시작 (상태 변경)
    public UserOrderEntity delivery(UserOrderEntity userorderEntity){
        userorderEntity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(userorderEntity, UserOrderStatus.DELIVERY);
    }

    // 배달 완료 (상태 변경)
    public UserOrderEntity receive(UserOrderEntity userorderEntity){
        userorderEntity.setReceivedAt(LocalDateTime.now());
        return setStatus(userorderEntity, UserOrderStatus.RECEIVE);
    }

}
