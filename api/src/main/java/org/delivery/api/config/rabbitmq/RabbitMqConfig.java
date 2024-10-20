package org.delivery.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public DirectExchange directExchange() {
        // "delivery.exchange"라는 이름의 Direct Exchange 생성
        return new DirectExchange("delivery.exchange");
    }

    @Bean
    public Queue queue(){
        // "delivery.queue"라는 이름의 큐 생성
        return new Queue("delivery.queue");
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue){
        // Queue 와 DirectExchange 를 바인딩하여 "delivery.key" 라우팅 키를 설정
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
    }

    // ===== END QUEQE 설정 =====

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        // RabbitTemplate 생성 및 메시지 변환기 설정
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        // Jackson2JsonMessageConverter 생성 및 ObjectMapper 설정
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
