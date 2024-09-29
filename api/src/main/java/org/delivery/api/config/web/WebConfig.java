package org.delivery.api.config.web;

import lombok.RequiredArgsConstructor;
import org.delivery.api.interceptor.AuthorizationInterceptor;
import org.delivery.api.resolver.UserSessionResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // AuthorizationInterceptor 인스턴스 주입
    private final AuthorizationInterceptor authorizationInterceptor;
    private final UserSessionResolver userSessionResolver;

    // 공개 API 경로 리스트
    private List<String> OPEN_API = List.of(
            "/open-api/**"
    );

    // 기본 제외 경로 리스트
    private List<String> DEFAULT_EXCLUDE = List.of(
            "/",
            "favicon.ico",
            "/error"
    );

    // Swagger 관련 경로 리스트
    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // AuthorizationInterceptor를 등록하고, 특정 경로는 제외하도록 설정
        registry.addInterceptor(authorizationInterceptor)
                // 공개 API 경로 제외
                .excludePathPatterns(OPEN_API)
                // 기본 제외 경로
                .excludePathPatterns(DEFAULT_EXCLUDE)
                // Swagger 관련 경로 제외
                .excludePathPatterns(SWAGGER);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // UserSessionResolver를 추가하여 사용자 세션 정보를 주입할 수 있도록 설정
        resolvers.add(userSessionResolver);
    }

}
