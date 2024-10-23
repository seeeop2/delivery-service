package org.delivery.api.domain.userorder.producer;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.rabbitmq.Producer;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.userorder.UserOrderEntity;
import org.springframework.stereotype.Service;

// 사용자 주문 메시지를 RabbitMQ에 전송하는 서비스 클래스

@RequiredArgsConstructor
@Service
public class UserOrderProducer {

    // RabbitMQ 메시지를 전송하는 Producer 의존성
    private final Producer producer;

    // 메시지를 전송할 Exchange 이름
    private static final String EXCHANGE = "delivery.exchange";
    // 메시지 라우팅을 위한 키
    private static final String ROUTE_KEY = "delivery.key";

    // UserOrderEntity를 받아 주문 ID를 기반으로 메시지를 전송하는 메서드
    public void sendOrder(UserOrderEntity userOrderEntity){
        // 주문 ID를 추출하여 sendOrder 메서드 호출
        sendOrder(userOrderEntity.getId());
    }

    // 주문 ID를 기반으로 UserOrderMessage를 생성하고 RabbitMQ에 전송하는 메서드
    public void sendOrder(Long userOrderId){
        // UserOrderMessage 객체 생성
        UserOrderMessage message = UserOrderMessage.builder()
                .userOrderId(userOrderId)
                .build();

        // 메시지를 RabbitMQ의 지정된 Exchange와 Route Key로 전송
        producer.producer(EXCHANGE, ROUTE_KEY, message);
    }

}
