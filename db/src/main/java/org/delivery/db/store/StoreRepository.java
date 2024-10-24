package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    // 유효한 스토어
    // SELECT * FROM STORE WHERE ID = ? AND STATUS = ? ORDER BY ID DESC LIMIT 1
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    // 유효한 스토어 리스트
    // SELECT * FROM STORE WHERE STATUS = ? ORDER BY ID DESC
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    // 유효한 특정 카테고리의 스토어 리스트
    // SELECT * FROM STORE WHERE STATUS = ? AND CATEGORY = ? ORDER BY STAR DESC
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);

    // 특정 스토어 이름과 상태로 유효한 스토어 조회 메서드
    // SELECT * FROM STORE WHERE NAME = ? AND STATUS = ? ORDER BY ID DESC LIMIT 1
    Optional<StoreEntity> findFirstByNameAndStatusOrderByIdDesc(String name, StoreStatus status);

}
