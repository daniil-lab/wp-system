package com.wp.system.services.subscription;

import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.subscription.SubscriptionRepository;
import com.wp.system.request.subscription.ExtendSubscriptionRequest;
import com.wp.system.request.subscription.UseVariantOnUserRequest;
import com.wp.system.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionVariantService subscriptionVariantService;

    @Autowired
    private AuthHelper authHelper;

    public Subscription getSubscriptionByUserId() {
        User user = authHelper.getUserFromAuthCredentials();

        return subscriptionRepository.getSubscriptionByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("User Subscription not found", HttpStatus.NOT_FOUND);
        });
    }
}
