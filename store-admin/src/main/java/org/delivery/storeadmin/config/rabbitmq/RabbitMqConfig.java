package org.delivery.storeadmin.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// RabbitMQ 관련 설정을 위한 Configuration 클래스

@Configuration
public class RabbitMqConfig {

    // Jackson2JsonMessageConverter를 Bean으로 등록하여 RabbitMQ 메시지를 JSON 형식으로 변환하는 설정
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        // ObjectMapper를 사용하여 메시지를 JSON 형식으로 변환하는 Jackson2JsonMessageConverter 반환
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
