package com.wp.system.repository.tinkoff;

import com.wp.system.entity.bill.BillTransaction;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TinkoffTransactionRepository extends JpaRepository<TinkoffTransaction, UUID> {
    @Query("SELECT t FROM TinkoffTransaction t WHERE t.tinkoffId = ?1 AND t.card.id = ?2")
    Optional<TinkoffTransaction> getTinkoffTransactionByTinkoffId(String id, UUID cardId);

    @Query("SELECT t FROM TinkoffTransaction t wHERE t.card.id = ?1")
    Page<TinkoffTransaction> findByCardId(UUID id, Pageable pageable);

    Page<TinkoffTransaction> findByCardIntegrationUserId(UUID id, Pageable pageable);

    List<TinkoffTransaction> findByCategoryIdAndDateGreaterThanEqual(UUID categoryId, Instant date);
}
