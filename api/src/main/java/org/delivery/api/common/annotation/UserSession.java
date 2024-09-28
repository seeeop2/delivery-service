package org.delivery.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 사용자 정보를 담고자 하는 어노테이션
// 이 어노테이션은 메서드의 매개변수에 사용되어, 사용자 세션 정보를 주입하는 데 사용됨.

// 이 어노테이션은 매개변수에 사용할 수 있도록 정의됨.
@Target(ElementType.PARAMETER)
// 런타임 동안 유지되어야 함을 명시함.
@Retention(RetentionPolicy.RUNTIME)
public @interface UserSession {
}
