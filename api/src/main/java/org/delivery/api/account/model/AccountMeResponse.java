package org.delivery.api.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
/*
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
해당 어노테이션은 해당 클래스에만 적용이 된다.
공통화 작업은 ObjectMapper 설정.
*/
public class AccountMeResponse {
    private String email;

    private String name;

    private LocalDateTime registeredAt;

}
