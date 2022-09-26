package com.wp.system.repository.loyalty;

import com.wp.system.entity.loyalty.LoyaltyCard;
import com.wp.system.entity.loyalty.LoyaltyCardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoyaltyCardImageRepository extends JpaRepository<LoyaltyCardImage, UUID> {
}
