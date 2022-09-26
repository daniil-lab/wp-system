package com.wp.system.repository.sber;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SberIntegrationRepository extends JpaRepository<SberIntegration, UUID> {
    @Query("SELECT s FROM SberIntegration s JOIN s.user u WHERE u.id = ?1")
    Optional<SberIntegration> getSberIntegrationByUserId(UUID id);
}
