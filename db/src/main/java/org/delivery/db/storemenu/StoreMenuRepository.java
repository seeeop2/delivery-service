package org.delivery.db.storemenu;

import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    // 유효한 메뉴 체크
    // SELECT * FROM STORE_MENU WHERE ID = ? AND STATUS = ? ORDER BY ID DESC LIMIT 1
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    // 특정 가게의 메뉴 가져오기
    // SELECT * FROM STORE_MENU WHERE STORE_ID = ? AND STATUS = ? ORDER BY SEQUENCE DESC
    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId, StoreMenuStatus status);

}
