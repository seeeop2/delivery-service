package org.delivery.storeadmin.domain.sse.connection.ifs;

// 연결 풀 인터페이스
public interface ConnectionPoolIfs<T, R> {

    // 세션을 추가하는 메서드
    void addSession(T uniqueKey, R session);

    // 고유 키에 해당하는 세션을 가져오는 메서드
    R getSession(T uniqueKey);

    // 세션의 완료 콜백 메서드
    void onCompletionCallback(R session);

}
