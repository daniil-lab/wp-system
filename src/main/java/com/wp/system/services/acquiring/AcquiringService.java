package com.wp.system.services.acquiring;

import com.wp.system.repository.subscription.SubscriptionVariantRepository;
import com.wp.system.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AcquiringService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionVariantRepository subscriptionVariantRepository;

    public String generatePaymentUrl(UUID userId, UUID subscriptionVariant) {
        return null;
    }
}
