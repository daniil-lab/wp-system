package com.wp.system.services.subscription;

import com.wp.system.entity.subscription.Subscription;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.subscription.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription getSubscriptionById(UUID id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("User Subscription not found", HttpStatus.NOT_FOUND);
        });
    }

    public List<Subscription> getAllSubscription() {
        return subscriptionRepository.findAll().stream().toList();
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
