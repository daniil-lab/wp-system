package com.wp.system.repository.sber;

import com.wp.system.entity.sber.SberCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SberCardRepository extends JpaRepository<SberCard, UUID> {
//    @Query("SELECT c FROM SberCard c JOIN c.sberIntegration s JOIN s.user u WHERE u.id = ?1")
    Optional<SberCard> findByCardIdAndIntegrationId(String cardId, UUID integrationId);

    List<SberCard> findByIntegrationId(UUID integrationId);

    Optional<SberCard> findByIntegrationIdAndId(UUID integrationId, UUID id);

    List<SberCard> findByIntegrationUserId(UUID userId);
}
