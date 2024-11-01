package org.delivery.storeadmin.domain.sse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;

import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// SSE API Controller

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    // 사용자 연결을 관리하기 위한 스레드 맵
    private static final Map<String, SseEmitter> userConnection = new ConcurrentHashMap<>();

    private final SseConnectionPool sseConnectionPool; // SSE 연결 풀
    private final ObjectMapper objectMapper; // JSON 직렬화를 위한 ObjectMapper

    // 클라이언트와의 SSE 연결을 처리하는 메서드
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {
        log.info("login user: {}", userSession);

        // UserSseConnection 인스턴스 생성
        UserSseConnection userSseConnection = UserSseConnection.connect(userSession.getStoreId().toString(), sseConnectionPool, objectMapper);

        // 연결 풀에 세션 추가
        sseConnectionPool.addSession(userSseConnection.getUniqueKey(), userSseConnection);

        // SSE Emitter 반환
        return userSseConnection.getSseEmitter();
    }

    // 클라이언트에 이벤트를 푸시하는 메서드
    @GetMapping("/push-evnet")
    public void pushEvent(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession){
        // 사용자 세션을 기반으로 UserSseConnection 가져오기
        UserSseConnection userSseConnection = sseConnectionPool.getSession(userSession.getStoreId().toString());

        // 세션이 존재하면 메시지 전송
        Optional.ofNullable(userSseConnection).ifPresent(it -> {
            // 클라이언트에 "hello world" 메시지 전송
            it.sendMessage("hello world");
        });
    }

}
