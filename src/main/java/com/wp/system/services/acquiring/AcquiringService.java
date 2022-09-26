package com.wp.system.services.acquiring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.entity.acquiring.Acquiring;
import com.wp.system.entity.subscription.Subscription;
import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.acquiring.AcquiringRepository;
import com.wp.system.repository.subscription.SubscriptionRepository;
import com.wp.system.repository.subscription.SubscriptionVariantRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.repository.user.UserRoleRepository;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.acquiring.tinkoff.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AcquiringService {
    @Autowired
    private AcquiringRepository acquiringRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionVariantRepository subscriptionVariantRepository;

    @Autowired
    private AuthHelper authHelper;

    public String generatePaymentUrl(UUID subscriptionVariant) {
        try {
            User user = authHelper.getUserFromAuthCredentials();

            SubscriptionVariant variant = subscriptionVariantRepository.findById(subscriptionVariant)
                    .orElseThrow(() -> {
                        throw new ServiceException("Subscription variant not found", HttpStatus.NOT_FOUND);
                    });

            InitPaymentRequest request = new InitPaymentRequest();
            InitPaymentItem initPaymentItem = new InitPaymentItem();
            InitPaymentData initPaymentData = new InitPaymentData();
            InitPaymentReceipt initPaymentReceipt = new InitPaymentReceipt();

            String orderId = UUID.randomUUID().toString();

            request.setOrderId(orderId);
            request.setTerminalKey("1648293941755DEMO");

            initPaymentData.setUserId(user.getId());
            initPaymentData.setSubVariantId(subscriptionVariant);
            request.setDATA(initPaymentData);

            initPaymentItem.setName(variant.getName());
            initPaymentItem.setQuantity(1);
            initPaymentItem.setAmount(variant.getNewPrice().intValue() * 100);
            initPaymentItem.setPrice(variant.getNewPrice().intValue() * 100);

            initPaymentReceipt.setItems(List.of(initPaymentItem));
            initPaymentReceipt.setPhone(user.getUsername());
            initPaymentReceipt.setEmailCompany("payment@walletbox.app");
            initPaymentReceipt.setEmail(user.getEmail().getAddress());

            request.setReceipt(initPaymentReceipt);
            request.setAmount(variant.getNewPrice().intValue() * 100);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper mapper = new ObjectMapper();

            ResponseEntity<String> response = restTemplate.exchange("https://securepay.tinkoff.ru/v2/Init",
                    HttpMethod.POST,
                    new HttpEntity<>(mapper.writeValueAsString(request), headers),
                    String.class);

            HashMap<String, Object> responseData = new ObjectMapper().readValue(response.getBody(), new TypeReference<HashMap<String, Object>>() {
            });

            if(!(Boolean) responseData.get("Success"))
                throw new ServiceException("Error response from Tinkoff", HttpStatus.INTERNAL_SERVER_ERROR);

            Acquiring acquiring = new Acquiring();

            acquiring.setVariantId(subscriptionVariant);
            acquiring.setUserId(user.getId());
            acquiring.setBankPaymentId(Long.parseLong((String) responseData.get("PaymentId")));
            acquiring.setAmount(variant.getNewPrice().intValue() * 100L);
            acquiring.setOrderId(orderId);
            acquiring.setTerminalKey("1648293941755DEMO");

            acquiringRepository.save(acquiring);

            return responseData.get("PaymentURL").toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public boolean checkPayment(String orderId) {
        try {
            Acquiring acquiring = acquiringRepository.findByOrderId(orderId).orElseThrow(() -> {
                throw new ServiceException("Error. Payment not found", HttpStatus.NOT_FOUND);
            });

            String stringForHash = "g3inn4sodn0zzx03" + acquiring.getBankPaymentId() + acquiring.getTerminalKey();

            System.out.println(stringForHash);

            String encodedhash = sha256(stringForHash);

            System.out.println(encodedhash);

            CheckPaymentRequest request = new CheckPaymentRequest();
            request.setTerminalKey(acquiring.getTerminalKey());
            request.setToken(encodedhash);
            request.setPaymentId(acquiring.getBankPaymentId());

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper mapper = new ObjectMapper();

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange("https://securepay.tinkoff.ru/v2/GetState",
                    HttpMethod.POST,
                    new HttpEntity<>(mapper.writeValueAsString(request), headers),
                    String.class);

            HashMap<String, Object> responseData = new ObjectMapper().readValue(response.getBody(), new TypeReference<HashMap<String, Object>>() {
            });

            if(!(Boolean) responseData.get("Success"))
                throw new ServiceException("Error response from Tinkoff", HttpStatus.INTERNAL_SERVER_ERROR);

            if(responseData.get("Status").equals("CONFIRMED")) {
                User user = userRepository.findById(acquiring.getUserId()).orElseThrow(() -> {
                    throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
                });

                SubscriptionVariant variant = subscriptionVariantRepository.findById(acquiring.getVariantId())
                        .orElseThrow(() -> {
                            throw new ServiceException("Subscription variant not found", HttpStatus.NOT_FOUND);
                        });

                Subscription subscription = subscriptionRepository.getSubscriptionByUserId(user.getId()).orElseThrow(() -> {
                    throw new ServiceException("Recreate user. Subscription not found.", HttpStatus.NOT_FOUND);
                });

                subscription.setActive(true);
                subscription.setStartDate(Instant.now());
                subscription.setEndDate(Instant.now().plus(variant.getExpiration(), ChronoUnit.DAYS));
                subscription.setVariant(variant);

                subscriptionRepository.save(subscription);

                user.setRole(variant.getRole());

                userRepository.save(user);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
