package com.wp.system.repository.tochkaa;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.entity.tochka.TochkaTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface TochkaTransactionRepository extends JpaRepository<TochkaTransaction, UUID> {
//    @Query("SELECT c FROM SberCard c JOIN c.sberIntegration s JOIN s.user u WHERE u.id = ?1")
//    Optional<SberTransaction> findByCardIdAndCardIntegrationIdAndSberId(UUID cardId, UUID integrationId, String sberId);
//
//    List<SberTransaction> findByCardId(UUID id);
//
    Page<TochkaTransaction> findByCardIntegrationUserId(UUID id, Pageable pageable);

    Page<TochkaTransaction> findByCardIdAndDateBetween(UUID id, Instant start, Instant end, Pageable pageable);

    List<TochkaTransaction> findByCategoryIdAndDateGreaterThanEqual(UUID categoryId, Instant date);
}
