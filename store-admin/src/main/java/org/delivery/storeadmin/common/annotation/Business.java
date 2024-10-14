package org.delivery.storeadmin.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 비즈니스 로직을 나타내는 사용자 정의 어노테이션

// 이 어노테이션은 클래스, 인터페이스, 열거형에 적용 가능
@Target(ElementType.TYPE)
// 런타임 시에 어노테이션 정보가 유지됨
@Retention(RetentionPolicy.RUNTIME)
// Spring의 Service 어노테이션을 포함하여 자동으로 서비스로 등록됨
@Service
public @interface Business {

    // Service 어노테이션의 value 속성을 재정의하기 위한 AliasFor
    @AliasFor(annotation = Service.class)
    String value() default "";

}
