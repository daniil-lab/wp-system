package com.wp.system.repository.tochkaa;

import com.wp.system.entity.tochka.TochkaIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TochkaIntegrationRepository extends JpaRepository<TochkaIntegration, UUID> {
    @Query("SELECT t FROM TochkaIntegration t JOIN t.user u WHERE u.id = ?1")
    Optional<TochkaIntegration> getTochkaIntegrationByUserId(UUID id);
}
