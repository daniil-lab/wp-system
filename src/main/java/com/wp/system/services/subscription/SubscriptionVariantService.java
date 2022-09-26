package com.wp.system.services.subscription;

import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.subscription.SubscriptionVariantGroup;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.subscription.SubscriptionVariantGroupRepository;
import com.wp.system.repository.subscription.SubscriptionVariantRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.request.subscription.CreateSubscriptionVariantGroupRequest;
import com.wp.system.request.subscription.CreateSubscriptionVariantRequest;
import com.wp.system.request.subscription.UpdateSubscriptionVariantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubscriptionVariantService {
    @Autowired
    private SubscriptionVariantRepository subscriptionVariantRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SubscriptionVariantGroupRepository subscriptionVariantGroupRepository;

    public SubscriptionVariantGroup getGroupById(UUID id) {
        SubscriptionVariantGroup group = subscriptionVariantGroupRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Group not found", HttpStatus.NOT_FOUND);
        });

        return group;
    }

    @Transactional
    public SubscriptionVariantGroup removeVariantGroup(UUID id) {
        SubscriptionVariantGroup group = subscriptionVariantGroupRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Group not found", HttpStatus.NOT_FOUND);
        });

        subscriptionVariantGroupRepository.delete(group);

        return group;
    }

    public List<SubscriptionVariantGroup> getVariantGroups() {
        return subscriptionVariantGroupRepository.findAll();
    }

    public SubscriptionVariantGroup createSubscriptionVariantGroup(CreateSubscriptionVariantGroupRequest request) {
        Set<SubscriptionVariant> variants = new HashSet<>();

        request.getVariantIds().forEach((item) -> {
            variants.add(subscriptionVariantRepository.findById(item).orElseThrow(() -> {
                throw new ServiceException("Subscription variant not found", HttpStatus.NOT_FOUND);
            }));
        });

        SubscriptionVariantGroup group = new SubscriptionVariantGroup();

        group.setName(request.getName());
        group.setVariants(variants);

        subscriptionVariantGroupRepository.save(group);

        return group;
    }

    public List<SubscriptionVariant> getAllSubscriptionVariants() {
        return subscriptionVariantRepository.findAll().stream().collect(Collectors.toList());
    }

    public SubscriptionVariant getSubscriptionVariantById(UUID id) {
        return subscriptionVariantRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Subscription Variant not found", HttpStatus.NOT_FOUND);
        });
    }

    public SubscriptionVariant createSubscriptionVariant(CreateSubscriptionVariantRequest request) {
        SubscriptionVariant variant = new SubscriptionVariant(
                request.getName(),
                request.getDescription(),
                request.getPrice());

        variant.setRole(userRoleRepository.findById(request.getRoleId()).orElseThrow(() -> {
            throw new ServiceException("Role not found", HttpStatus.NOT_FOUND);
        }));

        variant.setExpiration(request.getExpiration());
        variant.setNewPrice(request.getNewPrice());

        subscriptionVariantRepository.save(variant);

        return variant;
    }

    @Transactional
    public SubscriptionVariant removeSubscriptionVariant(UUID id) {
        SubscriptionVariant variant = this.getSubscriptionVariantById(id);

        subscriptionVariantRepository.delete(variant);

        return variant;
    }

    public SubscriptionVariant updateSubscriptionVariant(UpdateSubscriptionVariantRequest request, UUID id) {
        SubscriptionVariant variant = this.getSubscriptionVariantById(id);

        if(request.getExpiration() != null && request.getExpiration() != variant.getExpiration())
            variant.setExpiration(request.getExpiration());

        if(request.getPrice() != null && request.getPrice() != variant.getPrice())
            variant.setPrice(request.getPrice());

        if(request.getName() != null && !request.getName().equals(variant.getName()))
            variant.setName(request.getName());

        if(request.getDescription() != null && !request.getDescription().equals(variant.getDescription()))
            variant.setDescription(request.getDescription());

        if(request.getNewPrice() != null && request.getNewPrice() != variant.getNewPrice())
            variant.setNewPrice(request.getNewPrice());

        if(request.getRoleId() != null) {
            variant.setRole(userRoleRepository.findById(request.getRoleId()).orElseThrow(() -> {
                throw new ServiceException("Role not found", HttpStatus.NOT_FOUND);
            }));
        }

        subscriptionVariantRepository.save(variant);

        return variant;
    }
}
