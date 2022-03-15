package com.wp.system.repository.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TinkoffTransactionRepository extends JpaRepository<TinkoffTransaction, UUID> {
    @Query("SELECT t FROM TinkoffTransaction t WHERE t.tinkoffId = ?1 AND t.card.id = ?2")
    Optional<TinkoffTransaction> getTinkoffTransactionByTinkoffId(String id, UUID cardId);
}
