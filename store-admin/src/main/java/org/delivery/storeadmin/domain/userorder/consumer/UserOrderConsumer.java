package org.delivery.storeadmin.domain.userorder.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 사용자 주문 메시지를 처리하는 소비자 클래스

@Slf4j
@RequiredArgsConstructor
@Component
public class UserOrderConsumer {

    // RabbitMQ의 "delivery.queue"에서 메시지를 수신하여 처리하는 메서드
    // 지정된 큐에서 메시지를 수신
    @RabbitListener(queues = "delivery.queue")
    public void userOrderConsumer(UserOrderMessage userOrderMessage){
        // 수신한 메시지를 로그로 출력
        log.info("message queue >> {}", userOrderMessage);
    }
}