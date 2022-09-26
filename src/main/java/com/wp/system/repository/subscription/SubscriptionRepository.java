package com.wp.system.repository.subscription;

import com.wp.system.entity.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM subscription WHERE id = (SELECT subscription_id FROM _user WHERE id = ?1)")
    Optional<Subscription> getSubscriptionByUserId(UUID id);
}
