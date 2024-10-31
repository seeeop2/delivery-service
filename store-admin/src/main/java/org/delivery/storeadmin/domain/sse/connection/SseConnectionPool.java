package org.delivery.storeadmin.domain.sse.connection;

import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// SSE 연결 풀 구현 클래스
@Slf4j
@Component
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSseConnection> {

    // 사용자 SSE 연결을 저장하는 ConcurrentHashMap
    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    // 새로운 세션을 추가하는 메서드
    @Override
    public void addSession(String uniqueKey, UserSseConnection userSseConnection) {
        connectionPool.put(uniqueKey, userSseConnection);
    }

    // 고유 키에 해당하는 세션을 반환하는 메서드
    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }

    // 세션의 완료 콜백 메서드
    @Override
    public void onCompletionCallback(UserSseConnection session) {
        log.info("callback connection pool completion: {}", session);
        connectionPool.remove(session.getUniqueKey());
    }

}
