package com.wp.system.repository.tochkaa;

import com.wp.system.entity.tochka.TochkaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TochkaTransactionRepository extends JpaRepository<TochkaTransaction, UUID> {
//    @Query("SELECT c FROM SberCard c JOIN c.sberIntegration s JOIN s.user u WHERE u.id = ?1")
//    Optional<SberTransaction> findByCardIdAndCardIntegrationIdAndSberId(UUID cardId, UUID integrationId, String sberId);
//
//    List<SberTransaction> findByCardId(UUID id);
//
//    Page<SberTransaction> findByCardId(UUID id, Pageable pageable);
}
