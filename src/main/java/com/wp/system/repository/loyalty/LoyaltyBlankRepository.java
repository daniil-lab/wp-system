package com.wp.system.repository.loyalty;

import com.wp.system.entity.loyalty.LoyaltyBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoyaltyBlankRepository extends JpaRepository<LoyaltyBlank, UUID> {
}
