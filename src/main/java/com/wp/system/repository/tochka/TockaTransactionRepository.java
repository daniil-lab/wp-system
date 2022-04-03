package com.wp.system.repository.tochka;

import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.entity.tochka.sber.TochkaIntegration;
import com.wp.system.entity.tochka.sber.TochkaTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TockaTransactionRepository extends JpaRepository<TochkaTransaction, UUID> {
//    @Query("SELECT c FROM SberCard c JOIN c.sberIntegration s JOIN s.user u WHERE u.id = ?1")
//    Optional<SberTransaction> findByCardIdAndCardIntegrationIdAndSberId(UUID cardId, UUID integrationId, String sberId);
//
//    List<SberTransaction> findByCardId(UUID id);
//
//    Page<SberTransaction> findByCardId(UUID id, Pageable pageable);
}
