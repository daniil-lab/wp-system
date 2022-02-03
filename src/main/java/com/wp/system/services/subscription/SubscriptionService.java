package com.wp.system.services.subscription;

import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.subscription.SubscriptionRepository;
import com.wp.system.request.subscription.ExtendSubscriptionRequest;
import com.wp.system.request.subscription.UseVariantOnUserRequest;
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

    public Subscription getSubscriptionById(UUID id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("User Subscription not found", HttpStatus.NOT_FOUND);
        });
    }

    public List<Subscription> getAllSubscription() {
        return subscriptionRepository.findAll().stream().toList();
    }

    public Subscription resetSubscription(UUID id) {
        Subscription subscription = getSubscriptionById(id);

        subscription.setStartDate(null);
        subscription.setEndDate(null);
        subscription.setActive(false);

        subscriptionRepository.save(subscription);

        return subscription;
    }

    public Subscription useVariantOnUser(UseVariantOnUserRequest request) {
        SubscriptionVariant variant = subscriptionVariantService.getSubscriptionVariantById(request.getVariantId());

        Subscription subscription = getSubscriptionByUserId(request.getUserId());

        subscription.setStartDate(Instant.now());
        subscription.setEndDate(Instant.now().plus(variant.getExpiration(), ChronoUnit.DAYS));

        subscription.setVariant(variant);
        subscription.setActive(true);

        subscriptionRepository.save(subscription);

        return subscription;
    }

    public Subscription extendSubscription(ExtendSubscriptionRequest request, UUID id) {
        Subscription subscription = getSubscriptionById(id);

        subscription.setStartDate(Instant.now());
        subscription.setEndDate(subscription.getStartDate().plus(request.getDays(), ChronoUnit.DAYS));
        subscription.setActive(true);
        subscription.setVariant(null);

        subscriptionRepository.save(subscription);

        return subscription;
    }

    public Subscription getSubscriptionByUserId(UUID id) {
        return subscriptionRepository.getSubscriptionByUserId(id).orElseThrow(() -> {
            throw new ServiceException("User Subscription not found", HttpStatus.NOT_FOUND);
        });
    }

    public List<Subscription> getSubscriptionsByPage(int pageSize, int page) {
        return subscriptionRepository.findAll(PageRequest.of(page, pageSize)).stream().toList();
    }
}
