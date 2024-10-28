package org.delivery.storeadmin.domain.sse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sse")
public class SseApiController {

    // 사용자 연결을 관리하기 위한 스레드 맵
    private static final Map<String, SseEmitter> userConnection = new ConcurrentHashMap<>();

    // 클라이언트와의 SSE 연결을 처리하는 메서드
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession) {
        log.info("login user: {}", userSession);

        // 1분 타임아웃 설정
        SseEmitter emitter = new SseEmitter(1000L * 60);

        // 사용자 ID로 Emitter 저장
        userConnection.put(userSession.getUserId().toString(), emitter);

        // 클라이언트와 타임아웃이 일어났을 때
        emitter.onTimeout(() -> {
            log.info("on timeout");
            emitter.complete();
        });

        // 클라이언트와 연결이 종료됐을 때 처리
        emitter.onCompletion(() -> {
            log.info("completion");
            // 사용자 연결 제거
            userConnection.remove(userSession.getUserId().toString());
        });

        // 최초 연결 시 응답 전송
        SseEmitter.SseEventBuilder event = SseEmitter
                .event()
                // 이벤트 이름 설정
                .name("onopen");
        try {
            // 이벤트 전송
            emitter.send(event);
        } catch (IOException e) {
            // 오류 발생 시 Emitter 종료
            emitter.completeWithError(e);
        }
        return emitter;
    }

    // 클라이언트에 이벤트를 푸시하는 메서드
    @GetMapping("/push-evnet")
    public void pushEvent(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession){
        // 기존에 연결된 유저 찾기
        SseEmitter emitter = userConnection.get(userSession.getUserId().toString());
        // 전송할 이벤트 데이터 설정
        SseEmitter.SseEventBuilder event = SseEmitter
                // 별도로 이벤트 이름을 지정하는 상황이 된다면, event 메소드의 파라미터에 기입하면 됨.
                // 기입 안할 시, 기본값은 onmessage
                .event()
                // 데이터를 보내게 되면, 자동으로 onmessage에 쌓이게 됨.
                .data("hello");

        try {
            // 이벤트 전송
            emitter.send(event);
        } catch (IOException e){
            // 오류 발생 시 Emitter 종료
            emitter.completeWithError(e);
        }
    }

}
