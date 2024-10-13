package org.delivery.storeadmin.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// JPA 리포지토리가 위치한 패키지 설정
@EnableJpaRepositories(basePackages = "org.delivery.db")
// 엔티티 클래스가 위치한 패키지를 스캔
@EntityScan(basePackages = "org.delivery.db")
// Spring의 설정 클래스를 정의
@Configuration
public class JpaConfig {
}
