package com.wp.system.services.tinkoff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.entity.BankTransactionType;
import com.wp.system.entity.category.Category;
import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.category.CategoryRepository;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.repository.tinkoff.TinkoffTransactionRepository;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.request.tinkoff.UpdateTinkoffTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.ObjectToUrlEncodedMapper;
import com.wp.system.utils.tinkoff.TinkoffAuthRequest;
import com.wp.system.utils.tinkoff.TinkoffSync;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.utils.tinkoff.request.TinkoffSmsRequest;
import com.wp.system.utils.tinkoff.request.TinkoffSmsSubmitRequest;
import com.wp.system.utils.tinkoff.response.TinkoffAuthResponse;
import com.wp.system.utils.tinkoff.response.TinkoffSessionResponse;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TinkoffService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TinkoffIntegrationRepository tinkoffIntegrationRepository;

    @Autowired
    private TinkoffCardRepository tinkoffCardRepository;

    @Autowired
    private TinkoffTransactionRepository tinkoffTransactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthHelper authHelper;

    private List<TinkoffAuthChromeTab> tinkoffChromeTabs = new ArrayList<>();

    private List<TinkoffAuthRequest> authRequests = new ArrayList<>();

    public TinkoffIntegration getIntegrationByUserId() {
        User user = authHelper.getUserFromAuthCredentials();

        return tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId()).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });
    }

    public PagingResponse<TinkoffTransactionDTO> getTransactionsByCardId(UUID cardId, int page, int pageSize) {
        Page<TinkoffTransaction> tinkoffTransactions = tinkoffTransactionRepository.findByCardId(cardId, PageRequest.of(page, pageSize));

        return new PagingResponse<>(tinkoffTransactions.getContent().stream().map(TinkoffTransactionDTO::new).collect(Collectors.toList()),
                tinkoffTransactions.getTotalElements(), tinkoffTransactions.getTotalPages());
    }

    @Transactional
    public TinkoffIntegration removeIntegration() {
        TinkoffIntegration integration = getIntegrationByUserId();

        integration.setUser(null);

        tinkoffIntegrationRepository.delete(integration);

        return integration;
    }

    public TinkoffTransaction updateTinkoffTransaction(UpdateTinkoffTransactionRequest request, UUID transactionId) {
        User user = authHelper.getUserFromAuthCredentials();

        TinkoffTransaction transaction = tinkoffTransactionRepository.findById(transactionId).orElseThrow(() -> {
            throw new ServiceException("Tinkoff transaction not found", HttpStatus.NOT_FOUND);
        });

        Double transactionAmount = Double.parseDouble(transaction.getAmount().getAmount() + "." + transaction.getAmount().getCents());

        if(!transaction.getCard().getIntegration().getUser().getId().equals(user.getId()))
            throw new ServiceException("It`s not your transactions", HttpStatus.FORBIDDEN);

        if(request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> {
                throw new ServiceException("Category not found", HttpStatus.NOT_FOUND);
            });

            if(!category.getUser().getId().equals(user.getId()))
                throw new ServiceException("It`s not your category", HttpStatus.FORBIDDEN);

            if(category.isOnlyForEarn() && transaction.getTransactionType() == BankTransactionType.SPEND)
                throw new ServiceException("Given category only for earn", HttpStatus.BAD_REQUEST);

            if(transaction.getTransactionType() == BankTransactionType.SPEND) {
                category.setCategorySpend(category.getCategorySpend() + transactionAmount);

                if(category.getCategoryLimit() != 0) {
                    category.setPercentsFromLimit((category.getCategorySpend() / category.getCategoryLimit()) * 100);
                }
            } else {
                category.setCategoryEarn(category.getCategoryEarn() + transactionAmount);
            }

            transaction.setCategory(category);
        }

        tinkoffTransactionRepository.save(transaction);

        return transaction;
    }

    @Scheduled(fixedDelay = 900 * 100)
    public void cleanTabs() {
        tinkoffChromeTabs.removeIf(tab -> tab.getExpiredAt().isBefore(Instant.now()));
    }

    public TinkoffAuthRequest startTinkoffConnect(TinkoffStartAuthRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        TinkoffAuthRequest authRequest = new TinkoffAuthRequest();

        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedMapper(mapper));

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<TinkoffSessionResponse> sessionResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/session?appName=pfphome&appVersion=pfphome-prod-v0.30.4&origin=web%2Cib5%2Cplatform",
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                TinkoffSessionResponse.class);

        if(sessionResponse.getStatusCodeValue() == 200) {
            TinkoffSmsRequest body = new TinkoffSmsRequest();

            System.out.println(sessionResponse.getBody().getPayload());

            body.setPhone(request.getPhone());
            body.setPassword(request.getPassword());

            ResponseEntity<TinkoffAuthResponse> signUpResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/sign_up?sessionid=" + sessionResponse.getBody().getPayload() + "&appName=pfphome&appVersion=pfphome-prod-v0.30.4&origin=web%2Cib5%2Cplatform",
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    TinkoffAuthResponse.class);

            System.out.println(signUpResponse.getStatusCodeValue());
            System.out.println(signUpResponse.getBody());

            authRequest.setPassword(request.getPassword());
            authRequest.setPhone(request.getPhone());
            authRequest.setOperationTicket(signUpResponse.getBody().getOperationTicket());
            authRequest.setInitialOperation(signUpResponse.getBody().getInitialOperation());
            authRequest.setSessionId(sessionResponse.getBody().getPayload());
            authRequest.setReAuth(request.isReAuth());

            authRequests.add(authRequest);

            return authRequest;
        }

        return null;
//        String phone = null;
//        Instant startExportDate = null;
//        String password = null;
//
//        tinkoffChromeTabs.removeIf((val) -> val.getId().equals(user.getId()));
//
//        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());
//
//        if(request.isReAuth()) {
//            if(integration.isEmpty())
//                throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
//
//            phone = integration.get().getUsername();
//            startExportDate = integration.get().getStartDate();
//            password = integration.get().getPassword();
//        } else {
//            if (integration.isPresent())
//                throw new ServiceException("Integration already exist", HttpStatus.BAD_REQUEST);
//
//            if(request.getPhone() == null)
//                throw new ServiceException("Pass phone to request body", HttpStatus.BAD_REQUEST);
//
//            if(request.getExportStartDate() == null)
//                throw new ServiceException("Pass exportStartDate to request body", HttpStatus.BAD_REQUEST);
//
//            phone = request.getPhone();
//            startExportDate = request.getExportStartDate();
//        }
//        WebDriver driver = WebDriverCreator.create();
//
//        driver.get("https://www.tinkoff.ru/login/");
//
//        WebElement phoneInput = driver.findElement(By.id("phoneNumber"));
//        phoneInput.sendKeys(phone);
//
//        WebElement button = driver.findElement(By.id("submit-button"));
//
//        button.click();
//
//        TinkoffAuthChromeTab tab = new TinkoffAuthChromeTab(driver);
//        tab.setPhone(phone);
//        tab.setUserId(user.getId());
//        tab.setExportStartDate(startExportDate);
//        tab.setPassword(password);
//
//        this.tinkoffChromeTabs.add(tab);
//
//        return tab;
    }

    public Set<TinkoffCard> getCards() {
        User user = authHelper.getUserFromAuthCredentials();

        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());

        if(integration.isPresent())
            return integration.get().getCards();

        throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public Boolean sync() {
        User user = authHelper.getUserFromAuthCredentials();

        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());

        if(integration.isPresent()) {
            integration.get().setStage(TinkoffSyncStage.IN_SYNC);

            TinkoffSync tinkoffSync = new TinkoffSync(integration.get());
            tinkoffSync.setCardRepository(tinkoffCardRepository);
            tinkoffSync.setIntegrationRepository(tinkoffIntegrationRepository);
            tinkoffSync.setTransactionRepository(tinkoffTransactionRepository);

//            if(integration.get().getStage().equals(TinkoffSyncStage.IN_SYNC))
//                return false;

            new Thread(tinkoffSync::sync).start();

            return true;
        }

        throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
    }

    public Boolean submitTinkoffConnect(TinkoffSubmitAuthRequest request) {
        try {
            User user = authHelper.getUserFromAuthCredentials();

            TinkoffAuthRequest authRequest = null;

            for (TinkoffAuthRequest r : this.authRequests)
                if(r.getId().equals(request.getId()))
                    authRequest = r;

            if(authRequest != null) {
//                Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());

                ObjectMapper mapper = new ObjectMapper();
                RestTemplate restTemplate = new RestTemplate();

                restTemplate.getMessageConverters().add(new ObjectToUrlEncodedMapper(mapper));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                System.out.println(authRequest.getPassword());
                System.out.println(authRequest.getPhone());
                System.out.println(authRequest.getOperationTicket());
                TinkoffSmsSubmitRequest smsSubmitRequest = new TinkoffSmsSubmitRequest();

                smsSubmitRequest.setInitialOperation("sign_up");
                smsSubmitRequest.setInitialOperationTicket(authRequest.getOperationTicket());
                smsSubmitRequest.setConfirmationData("{\"SMSBYID\":" + Integer.parseInt(request.getCode()) + "}");

                HttpEntity<TinkoffSmsSubmitRequest> requestData = new HttpEntity<>(smsSubmitRequest, headers);

                ResponseEntity<String> signUpResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/confirm?sessionid=" + authRequest.getSessionId() + "&origin=web%2Cib5%2Cplatform",
                        HttpMethod.POST,
                        requestData,
                        String.class);

                System.out.println(signUpResponse.getBody());

                ResponseEntity<String> levelUp = restTemplate.exchange("https://api.tinkoff.ru/v1/level_up?sessionid=" + authRequest.getSessionId() + "&origin=web%2Cib5%2Cplatform",
                        HttpMethod.POST,
                        new HttpEntity<>(null, null),
                        String.class);

                System.out.println(levelUp.getBody());

//                if(integration.isPresent()) {
////                    integration.get().setToken(tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue());
////                    integration.get().setWuid(tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("__P__wuid").getValue());
//
////                if(integration.get().getStage().equals(TinkoffSyncStage.IN_SYNC))
////                    return false;
//
//                    tinkoffIntegrationRepository.save(integration.get());
//                } else {
////                    TinkoffIntegration newIntegration = new TinkoffIntegration(userService.getUserById(tinkoffAuthChromeTab.getUserId()),
////                            tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue(),
////                            tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("__P__wuid").getValue());
////
////                    newIntegration.setPassword(request.getPassword());
////                    newIntegration.setUsername(tinkoffAuthChromeTab.getPhone());
////                    newIntegration.setStartDate(tinkoffAuthChromeTab.getExportStartDate());
////
////                    tinkoffIntegrationRepository.save(newIntegration);
//                }
//
//                tinkoffAuthChromeTab.getDriver().quit();
//
//                tinkoffChromeTabs.remove(tinkoffAuthChromeTab);
//
//                return true;
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            tinkoffChromeTabs.removeIf((val) -> val.getId().equals(request.getId()));
            throw new ServiceException("Auth page element not found", HttpStatus.BAD_REQUEST);
        }

        throw new ServiceException("Start auth stage not found", HttpStatus.NOT_FOUND);
    }
}
