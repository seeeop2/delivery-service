package org.delivery.api.domain.store.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.store.enums.StoreCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRegisterRequest {

    // @NotBlank: "", " ", null 막음
    // @NotNull: null 막음

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    // 카테고리는 Enum 타입으로, null 값이 들어오지 않도록 @NotNull을 사용하여 유효성 검사를 수행함.
    @NotNull
    private StoreCategory storeCategory;

    @NotBlank
    private String thumbnailUrl;

    @NotNull
    private BigDecimal minimumAmount;

    @NotNull
    private BigDecimal minimumDeliveryAmount;

    @NotBlank
    private String phoneNumber;

}
