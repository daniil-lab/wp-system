package com.wp.system.services.tochka;

import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.dto.tochka.TochkaTransactionDTO;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.entity.tochka.TochkaCard;
import com.wp.system.entity.tochka.TochkaIntegration;
import com.wp.system.entity.tochka.TochkaTransaction;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.repository.tochkaa.*;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.tinkoff.UpdateTinkoffTransactionRequest;
import com.wp.system.request.tochka.CreateTochkaIntegrationRequest;
import com.wp.system.request.tochka.UpdateTochkaTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.bill.TransactionType;
import com.wp.system.utils.tochka.TochkaSync;
import com.wp.system.utils.tochka.request.TochkaAuthCodeRequest;
import com.wp.system.utils.tochka.request.TochkaAuthRequest;
import com.wp.system.utils.tochka.response.TochkaAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TochkaService {

    @Autowired
    private TochkaIntegrationRepository tochkaIntegrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TochkaCardRepository tochkaCardRepository;

    @Autowired
    private TochkaTransactionRepository tochkaTransactionRepository;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public TochkaIntegration removeIntegration() {
        User user = authHelper.getUserFromAuthCredentials();

        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });
        integration.setUser(null);

        tochkaIntegrationRepository.delete(integration);

        return integration;
    }

    public TochkaIntegration submitCreate(CreateTochkaIntegrationRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        if(tochkaIntegrationRepository.getTochkaIntegrationByUserId(user.getId()).isPresent())
                throw new ServiceException("Integration already exist", HttpStatus.BAD_REQUEST);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        TochkaAuthCodeRequest authRequest = new TochkaAuthCodeRequest();

        authRequest.setClient_id("C1VLYt1nQC8QPtDPyVPf9pfNsRXXCrom");
        authRequest.setCode(request.getCode());
        authRequest.setGrant_type("authorization_code");
        authRequest.setClient_secret("d4RRWOBeIo9nstgNuEEcy75LD9VJwzHn");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<TochkaAuthResponse> tochkaResponse = restTemplate.exchange("https://enter.tochka.com/api/v1/oauth2/token",
                HttpMethod.POST,
                new HttpEntity<>(authRequest, headers),
                TochkaAuthResponse.class);

        TochkaIntegration integration = new TochkaIntegration();

        integration.setRefreshToken(tochkaResponse.getBody().getRefresh_token());
        integration.setStartDate(request.getStartDate());
        integration.setUser(user);

        tochkaIntegrationRepository.save(integration);

        return integration;
    }

    public Boolean sync() {
        User user = authHelper.getUserFromAuthCredentials();

        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        new Thread(() -> {
            new TochkaSync(integration, tochkaCardRepository).sync();
        }).start();

        return true;
    }

    public TochkaIntegration getIntegration() {
        User user = authHelper.getUserFromAuthCredentials();


        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        return integration;
    }

    public Set<TochkaCard> getCards() {
        User user = authHelper.getUserFromAuthCredentials();

        TochkaIntegration integration = tochkaIntegrationRepository.getTochkaIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        return integration.getCards();
    }

    public PagingResponse<TochkaTransactionDTO> getTransactionsByCardId(UUID cardId, Instant startDate, Instant endDate, int page, int pageSize) {
        Page<TochkaTransaction> transactions = tochkaTransactionRepository.findByCardIdAndDateBetween(cardId, startDate, endDate, PageRequest.of(page, pageSize));

        return new PagingResponse<>(transactions.getContent().stream().map(TochkaTransactionDTO::new).collect(Collectors.toList()),
                transactions.getTotalElements(), transactions.getTotalPages());
    }

    public TochkaTransaction updateTochkaTransaction(UpdateTochkaTransactionRequest request, UUID transactionId) {
        User user = authHelper.getUserFromAuthCredentials();

        TochkaTransaction transaction = tochkaTransactionRepository.findById(transactionId).orElseThrow(() -> {
            throw new ServiceException("Tinkoff transaction not found", HttpStatus.NOT_FOUND);
        });

        if(!transaction.getCard().getIntegration().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your transactions", HttpStatus.FORBIDDEN);

        if(request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> {
                throw new ServiceException("Category not found", HttpStatus.NOT_FOUND);
            });

            if(!category.getUser().getId().equals(user.getId()))
                throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

            if(TransactionType.WITHDRAW == transaction.getTransactionType()) {
                if(!category.getForSpend())
                    throw new ServiceException("Указанная категория не может содержать пополнений", HttpStatus.BAD_REQUEST);
            }

            if(TransactionType.DEPOSIT == transaction.getTransactionType()) {
                if(!category.getForEarn())
                    throw new ServiceException("Указанная категория не может содержать пополнений", HttpStatus.BAD_REQUEST);
            }

            if(transaction.getTransactionType() == TransactionType.WITHDRAW) {
                category.setCategorySpend(category.getCategorySpend().add(transaction.getAmount()));

                if(category.getCategoryLimit().compareTo(BigDecimal.ZERO) != 0) {
                    category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING).multiply(new BigDecimal("100"))));
                }
            } else {
                category.setCategoryEarn(category.getCategoryEarn().add(transaction.getAmount()));
            }

            transaction.setCategory(category);
        }

        tochkaTransactionRepository.save(transaction);

        return transaction;
    }
}
