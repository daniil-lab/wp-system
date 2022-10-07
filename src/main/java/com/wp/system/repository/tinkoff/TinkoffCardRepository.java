package com.wp.system.repository.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TinkoffCardRepository extends JpaRepository<TinkoffCard, UUID> {
    @Query("SELECT t FROM TinkoffCard t WHERE t.cardId = ?1 AND t.integration.user.id = ?2")
    Optional<TinkoffCard> getCardByCardId(String id, UUID userId);

    List<TinkoffCard> findByIntegrationUserId(UUID id);

    Optional<TinkoffCard> findByIntegrationIdAndId(UUID integrationId, UUID id);
}
