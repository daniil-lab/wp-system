package com.wp.system.repository.sber;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.sber.SberCard;
import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.entity.tochka.TochkaTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SberTransactionRepository extends JpaRepository<SberTransaction, UUID> {
//    @Query("SELECT c FROM SberCard c JOIN c.sberIntegration s JOIN s.user u WHERE u.id = ?1")
    Optional<SberTransaction> findByCardIdAndCardIntegrationIdAndSberId(UUID cardId, UUID integrationId, String sberId);

    List<SberTransaction> findByCardId(UUID id);

    Page<SberTransaction> findByCardIdAndDateBetween(UUID id, Instant start, Instant end, Pageable pageable);

    Page<SberTransaction> findByCardIntegrationUserId(UUID id, Pageable pageable);

    List<SberTransaction> findByCategoryIdAndDateGreaterThanEqual(UUID categoryId, Instant date);
}
