package org.delivery.storeadmin.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();

        //ObjectMapper를 커스텀해서 쓰기 위함.
        //JDK8버전 이후에 나온 클래스들을 처리해 주기 위한 모듈
        objectMapper.registerModule(new Jdk8Module());

        //local date
        objectMapper.registerModule(new JavaTimeModule());

        //모르는 json field에 대해서는 무시한다.
        //예를 들어, 현재 AccountMeResponse에 email, name, registeredAt 세 가지 속성이 있다.
        //request 될 때, 세 가지 항목 외에 다른 항목이 있을 경우 에러를 터트릴지 말지 결정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        //비어있는 빈을 만들 때 어떻게 설정할지 결정
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

        //날짜 관련 직렬화
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //스네이크 케이스 설정
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        return objectMapper;
    }
}
