package org.delivery.api.common.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    // 메시지를 특정 Exchange 로 전송하는 메서드
    public void producer(String exchange, String routeKey, Object object) {
        // 지정된 Exchange 와 라우팅 키를 사용하여 메시지를 전송
        rabbitTemplate.convertAndSend(exchange, routeKey, object);
    }
}
