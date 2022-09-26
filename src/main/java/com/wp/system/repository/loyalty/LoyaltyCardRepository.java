package com.wp.system.repository.loyalty;

import com.wp.system.entity.category.Category;
import com.wp.system.entity.loyalty.LoyaltyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, UUID> {
    @Query("SELECT c FROM LoyaltyCard c JOIN c.user u WHERE u.id = ?1")
    List<LoyaltyCard> getAllUserCards(UUID userId);
}
