package com.wp.system.repository.loyalty;

import com.wp.system.entity.loyalty.LoyaltyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, UUID> {
}
