package com.wp.system.repository.tochkaa;

import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tochka.TochkaCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TochkaCardRepository extends JpaRepository<TochkaCard, UUID> {
//    @Query("SELECT c FROM SberCard c JOIN c.sberIntegration s JOIN s.user u WHERE u.id = ?1")
//    Optional<TochkaCard> findByCardIdAndIntegrationId(String cardId, UUID integrationId);
//
//    List<TochkaCard> findByIntegrationId(UUID integrationId);
//
//    List<TochkaCard> findByIntegrationUserId(UUID userId);

    Optional<TochkaCard> findByCardNumberAndIntegrationId(String cardNumber, UUID integrationId);

    List<TochkaCard> findByIntegrationUserId(UUID id);
}
