package org.delivery.storeadmin.domain.sse.connection.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;
    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;
    private final ObjectMapper objectMapper;

    // UserSseConnection 생성자
    private UserSseConnection(String uniqueKey, ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs, ObjectMapper objectMapper) {
        // Key 초기화
        this.uniqueKey = uniqueKey;
        // SSE 초기화 (60초 타임아웃)
        this.sseEmitter = new SseEmitter(60 * 1000L);
        // Connection Pool 초기화
        this.connectionPoolIfs = connectionPoolIfs;
        // ObjectMapper 초기화
        this.objectMapper = objectMapper;

        // on completion
        this.sseEmitter.onCompletion(() -> {
            // connection pool remove
            connectionPoolIfs.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        // 최초 연결 시 메시지 전송 (onopen 메시지)
        sendMessage("onopen", "connect");
    }

    // UserSseConnection 인스턴스를 생성하는 정적 메서드
    public static UserSseConnection connect(String uniqueKey, ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs, ObjectMapper objectMapper){
        return new UserSseConnection(uniqueKey, connectionPoolIfs, objectMapper);
    }

    // 특정 이벤트와 데이터로 SSE 메시지를 전송하는 메서드
    public void sendMessage(String eventName, Object data) {
        try {
            // 데이터를 JSON으로 직렬화
            String json = this.objectMapper.writeValueAsString(data);
            // SSE 이벤트 생성
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                .name(eventName)
                .data(json);
            // 메시지 전송
            this.sseEmitter.send(event);
        } catch (IOException e) {
            // 오류 발생 시 SSE 종료
            this.sseEmitter.completeWithError(e);
        }
    }

    // 데이터로 SSE 메시지를 전송하는 메서드 (이벤트 이름 없음)
    public void sendMessage(Object data){
        try {
            // 데이터를 JSON으로 직렬화
            String json = this.objectMapper.writeValueAsString(data);

            // SSE 이벤트 생성 (기본적으로 onmessage 사용)
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .data(json);
            // 메시지 전송
            this.sseEmitter.send(event);
        } catch (IOException e) {
            // 오류 발생 시 SSE 종료
            this.sseEmitter.completeWithError(e);
        }
    }

}
