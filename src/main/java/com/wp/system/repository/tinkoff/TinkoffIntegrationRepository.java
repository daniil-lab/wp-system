package com.wp.system.repository.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TinkoffIntegrationRepository extends JpaRepository<TinkoffIntegration, UUID> {
    @Query("SELECT t FROM TinkoffIntegration t JOIN t.user u WHERE u.id = ?1")
    Optional<TinkoffIntegration> getTinkoffIntegrationByUserId(UUID id);
}
