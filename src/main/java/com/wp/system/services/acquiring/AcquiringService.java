package com.wp.system.services.acquiring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.entity.subscription.SubscriptionVariant;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.subscription.SubscriptionVariantRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.utils.acquiring.tinkoff.InitPaymentData;
import com.wp.system.utils.acquiring.tinkoff.InitPaymentItem;
import com.wp.system.utils.acquiring.tinkoff.InitPaymentReceipt;
import com.wp.system.utils.acquiring.tinkoff.InitPaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AcquiringService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionVariantRepository subscriptionVariantRepository;

    public String generatePaymentUrl(UUID userId, UUID subscriptionVariant) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> {
                throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
            });

            SubscriptionVariant variant = subscriptionVariantRepository.findById(subscriptionVariant)
                    .orElseThrow(() -> {
                        throw new ServiceException("Subscription variant not found", HttpStatus.NOT_FOUND);
                    });

            InitPaymentRequest request = new InitPaymentRequest();
            InitPaymentItem initPaymentItem = new InitPaymentItem();
            InitPaymentData initPaymentData = new InitPaymentData();
            InitPaymentReceipt initPaymentReceipt = new InitPaymentReceipt();

            request.setAmount(variant.getNewPrice().intValue() * 100);
            request.setOrderId(UUID.randomUUID().toString());
            request.setDescription(variant.getDescription());
            request.setTerminalKey("1648293941755DEMO");
            request.setFailURL("http://localhost:3000/subs/" + subscriptionVariant);
            request.setSuccessURL("http://localhost:3000/settings/");

            initPaymentData.setUserId(userId);
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

            System.out.println(request);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper mapper = new ObjectMapper();

            System.out.println(mapper.writeValueAsString(request));

            ResponseEntity<String> response = restTemplate.exchange("https://securepay.tinkoff.ru/v2/Init",
                    HttpMethod.POST,
                    new HttpEntity<>(mapper.writeValueAsString(request), headers),
                    String.class);

            System.out.println(response.getBody());
            System.out.println(response.getStatusCodeValue());

            HashMap<String, Object> responseData = new ObjectMapper().readValue(response.getBody(), new TypeReference<HashMap<String, Object>>() {
            });

            return responseData.get("PaymentURL").toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
