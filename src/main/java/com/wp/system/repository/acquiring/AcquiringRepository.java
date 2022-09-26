package com.wp.system.repository.acquiring;

import com.wp.system.entity.acquiring.Acquiring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcquiringRepository extends JpaRepository<Acquiring, UUID> {
    Optional<Acquiring> findByOrderId(String orderId);
}
