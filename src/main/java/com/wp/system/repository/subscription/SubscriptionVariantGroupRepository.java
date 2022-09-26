package com.wp.system.repository.subscription;

import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.subscription.SubscriptionVariantGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionVariantGroupRepository extends JpaRepository<SubscriptionVariantGroup, UUID> {
}
