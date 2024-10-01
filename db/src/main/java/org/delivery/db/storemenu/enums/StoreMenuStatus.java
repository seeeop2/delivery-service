package org.delivery.db.storemenu.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreMenuStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    // 상태에 대한 설명
    private String description;

}
