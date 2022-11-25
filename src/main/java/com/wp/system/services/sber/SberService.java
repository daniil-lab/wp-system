package com.wp.system.services.sber;

import com.wp.system.dto.sber.SberCardDTO;
import com.wp.system.dto.sber.SberTransactionDTO;
import com.wp.system.dto.tinkoff.TinkoffCardDTO;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.sber.SberCard;
import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.sber.SberTransaction;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.repository.sber.SberCardRepository;
import com.wp.system.repository.sber.SberIntegrationRepository;
import com.wp.system.repository.sber.SberTransactionRepository;
import com.wp.system.request.HideCardRequest;
import com.wp.system.request.sber.CreateSberIntegrationRequest;
import com.wp.system.request.sber.SubmitCreateSberIntegrationRequest;
import com.wp.system.request.sber.UpdateSberTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.bill.TransactionType;
import com.wp.system.utils.sber.SberAuth;
import com.wp.system.utils.sber.SberRegister;
import com.wp.system.utils.sber.SberSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SberService {
    @Autowired
    private SberIntegrationRepository sberIntegrationRepository;

    @Autowired
    private SberCardRepository sberCardRepository;

    @Autowired
    private SberTransactionRepository sberTransactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthHelper authHelper;

    private List<SberRegister> registerList = new ArrayList<>();

    public SberCardDTO hideCard(HideCardRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        SberIntegration integration = sberIntegrationRepository.getSberIntegrationByUserId(user.getId())
                .orElseThrow(() -> {
                    throw new ServiceException("Интеграция не найдена", HttpStatus.NOT_FOUND);
                });

        SberCard card = sberCardRepository.findByIntegrationIdAndId(integration.getId(), request.getId()).orElseThrow(() -> {
            throw new ServiceException("Карта не найдена", HttpStatus.NOT_FOUND);
        });

        card.setHidden(request.getHidden());

        sberCardRepository.save(card);

        return new SberCardDTO(card);
    }

    public SberTransaction updateSberTransaction(UpdateSberTransactionRequest request, UUID transactionId) {
        SberTransaction transaction = sberTransactionRepository.findById(transactionId).orElseThrow(() -> {
            throw new ServiceException("Sber transaction not found", HttpStatus.NOT_FOUND);
        });

        User user = authHelper.getUserFromAuthCredentials();

        if(!transaction.getCard().getIntegration().getUser().getId().equals(user.getId()))
            throw new ServiceException("No your object", HttpStatus.FORBIDDEN);

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
                    category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100")));
                }
            } else {
                category.setCategoryEarn(category.getCategoryEarn().add(transaction.getAmount()));
            }

            transaction.setCategory(category);
        }

        sberTransactionRepository.save(transaction);

        return transaction;
    }

    public boolean removeSberIntegration() {
        User user = authHelper.getUserFromAuthCredentials();

        SberIntegration sberIntegration = sberIntegrationRepository.getSberIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Sber integration not found", HttpStatus.NOT_FOUND);
        });

        sberIntegration.setUser(null);

        sberIntegrationRepository.delete(sberIntegration);

        return true;
    }

    public boolean startCreateSberIntegration(CreateSberIntegrationRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        registerList.removeIf(register -> register.getUserId().equals(user.getId()));

        sberIntegrationRepository.getSberIntegrationByUserId(user.getId()).ifPresent((val) -> {
            throw new ServiceException("Sber integration already exists", HttpStatus.BAD_REQUEST);
        });

        SberRegister sberRegister = new SberRegister(user.getId(), request.getPhone());

        sberRegister.setStartExportDate(request.getStartExportDate());
        sberRegister.register();

        registerList.add(sberRegister);

        return true;
    }

    public SberIntegration submitCreateSberIntegration(SubmitCreateSberIntegrationRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        SberRegister sberRegister = null;

        for (SberRegister r : registerList)
            if(r.getUserId().equals(user.getId())) {
                sberRegister = r;
                break;
            }

        if(sberRegister == null)
            throw new ServiceException("Register session not found", HttpStatus.BAD_REQUEST);

        sberRegister.submitRegister(request.getCode());
        sberRegister.createPin();

        SberIntegration integration = new SberIntegration();
        integration.setUser(userService.getUserById(sberRegister.getUserId()));
        integration.setToken(sberRegister.getToken());
        integration.setHost(sberRegister.getHost());
        integration.setmGUID(sberRegister.getmGUID());
        integration.setSession(sberRegister.getjSession());
        integration.setStartDate(sberRegister.getStartExportDate());

        System.out.println(integration.getHost());

        sberIntegrationRepository.save(integration);

        return integration;
    }

    public SberIntegration getSberIntegrationByUserId() {
        User user = authHelper.getUserFromAuthCredentials();


        return sberIntegrationRepository.getSberIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Sber integration not found", HttpStatus.BAD_REQUEST);
        });
    }

    public boolean syncSber() {
        User user = authHelper.getUserFromAuthCredentials();

        SberAuth sberAuth = new SberAuth(sberIntegrationRepository, getSberIntegrationByUserId());

        sberAuth.auth();

        SberSync sberSync = new SberSync(user.getId(), sberCardRepository, sberTransactionRepository, sberIntegrationRepository);

        new Thread(sberSync::sync).start();

        return true;
    }

    public PagingResponse<SberTransactionDTO> getTransactionsByCardId(UUID cardId, Instant startDate, Instant endDate, int page, int pageSize) {
        User user = authHelper.getUserFromAuthCredentials();

        SberCard card = sberCardRepository.findById(cardId).orElseThrow(() -> {
            throw new ServiceException("Card not found", HttpStatus.NOT_FOUND);
        });

        if(!card.getIntegration().getUser().getId().equals(user.getId()))
            throw new ServiceException("No your object", HttpStatus.FORBIDDEN);

        Page<SberTransaction> sberTransactions = sberTransactionRepository.findByCardIdAndDateBetween(cardId, startDate, endDate, PageRequest.of(page, pageSize));

        return new PagingResponse<>(sberTransactions.getContent().stream().map(SberTransactionDTO::new).collect(Collectors.toList()),
                sberTransactions.getTotalElements(), sberTransactions.getTotalPages());
    }

    @Scheduled(fixedDelay = 900 * 100)
    public void cleanTabs() {
        registerList.removeIf(register -> register.getCreateAt().isBefore(Instant.now()));
    }

    public List<SberCard> getUserSberCards() {
        User user = authHelper.getUserFromAuthCredentials();

        return sberCardRepository.findByIntegrationUserId(user.getId());
    }

    public SberIntegration getSberIntegration() {
        User user = authHelper.getUserFromAuthCredentials();

        return sberIntegrationRepository.getSberIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.BAD_REQUEST);
        });
    }
}
