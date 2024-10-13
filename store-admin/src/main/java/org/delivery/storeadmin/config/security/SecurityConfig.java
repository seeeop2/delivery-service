package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

// Security 활성화
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Swagger 관련 경로 리스트
    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // CSRF 보호 비활성화
            .csrf().disable()
            .authorizeHttpRequests(it -> {
                it.requestMatchers(
                    // 정적 리소스에 대해서는 모든 요청 허용
                    PathRequest.toStaticResources().atCommonLocations()
                // resource 에 대해서는 모든 요청 허용
                ).permitAll()

                // Swagger 는 인증 없이 통과
                .mvcMatchers(
                    SWAGGER.toArray(new String[0])
                ).permitAll()

                // open-api/** 하위 모든 주소는 인증 없이 통과
                .mvcMatchers(
                    "/open-api/**"
                ).permitAll()

                // 그 외 모든 요청은 인증 사용
                .anyRequest().authenticated();
            })
            // 기본 폼 로그인 설정
            .formLogin(Customizer.withDefaults());

        // 보안 필터 체인 반환
        return httpSecurity.build();
    }
}
