package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

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

        // 우선 ModHeader 크롬 확장프로그램을 설치하여,
        // 로그인 후 발급받은 JWT 토큰을 임의로 authorization-token 헤더명으로 등록
        // 테스트 시, authorization-token 헤더 가져올 수 있음.
        String accessToken = request.getHeader("authorization-token");

        // 토큰이 없을 경우 예외 발생
        if (accessToken == null) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        // 액세스 토큰의 유효성을 검증하고 사용자 ID를 반환
        Long userId = tokenBusiness.validationAccessToken(accessToken);

        if (userId != null) {
            // 현재 요청의 속성을 가져옴. 요청이 없을 경우 null이 반환될 수 있으므로, null 체크를 수행하여 예외를 던짐.
            RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            // 인증된 사용자 ID를 현재 요청의 속성에 저장.
            // "userId"라는 키로 userId 값을 요청 범위에 설정.
            // 이 값은 요청이 종료되면 소멸됨.
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);

            // 인증 성공
            return true;
        }

        // 인증 실패
        throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
    }
}
