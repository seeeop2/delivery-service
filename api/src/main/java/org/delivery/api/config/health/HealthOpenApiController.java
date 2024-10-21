package org.delivery.api.config.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.rabbitmq.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/open-api")
@RestController
public class HealthOpenApiController {

    // 메시지를 전송할 Producer 인스턴스
    private final Producer producer;

    // Producer - Exchange 간 전송이 잘 되는지 확인을 위한 메서드
    // 아무 의미 없음.
    @GetMapping("/health")
    public void health(){
        // 해당 메서드에 도달하는지 확인을 위한 로그
        log.info("health call");
        // 메시지를 RabbitMQ로 전송
        producer.producer("delivery.exchange", "delivery.key", "hello");
    }

}
