package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 요청 URI를 로그에 기록
        log.info("AuthorizationInterceptor url: {}", request.getRequestURI());

        // WEB 특히 Chrome 의 경우, OPTIONS 이라는 메소드를 사전에 실행하여, 해당 메소드를 지원하는지 체크함.
        if(HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 정적 리소스 요청(js, html, png 등)에 대해서 통과
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // TODO header 검증


        // 우선 통과 처리
        return true;
    }
}
