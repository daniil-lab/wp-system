package com.wp.system.services.tinkoff;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wp.system.dto.tinkoff.TinkoffCardDTO;
import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
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
import com.wp.system.request.HideCardRequest;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.request.tinkoff.UpdateTinkoffTransactionRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.AuthHelper;
import com.wp.system.utils.ObjectToUrlEncodedMapper;
import com.wp.system.utils.bill.TransactionType;
import com.wp.system.utils.tinkoff.TinkoffAuthRequest;
import com.wp.system.utils.tinkoff.TinkoffSync;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.utils.tinkoff.WebDriverCreator;
import com.wp.system.utils.tinkoff.request.TinkoffSmsDataRequest;
import com.wp.system.utils.tinkoff.request.TinkoffSmsRequest;
import com.wp.system.utils.tinkoff.request.TinkoffSmsSubmitRequest;
import com.wp.system.utils.tinkoff.response.TinkoffAuthResponse;
import com.wp.system.utils.tinkoff.response.TinkoffSessionResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    public PagingResponse<TinkoffTransaction> getTransactionsByCardId(UUID cardId, int page, int pageSize, Instant startDate, Instant endDate) {
        Page<TinkoffTransaction> tinkoffTransactions = tinkoffTransactionRepository.findByCardIdAndDateBetween(cardId,
                startDate, endDate, PageRequest.of(page, pageSize));

        return new PagingResponse<>(tinkoffTransactions.getContent(),
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
                    category.setPercentsFromLimit((category.getCategorySpend().divide(category.getCategoryLimit(), 2, RoundingMode.CEILING)).multiply(new BigDecimal("100")));
                }
            } else {
                category.setCategoryEarn(category.getCategoryEarn().add(transaction.getAmount()));
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

    public TinkoffCardDTO hideCard(HideCardRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

        TinkoffIntegration integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId())
                .orElseThrow(() -> {
                    throw new ServiceException("Интеграция не найдена", HttpStatus.NOT_FOUND);
                });

        TinkoffCard card = tinkoffCardRepository.findByIntegrationIdAndId(integration.getId(), request.getId()).orElseThrow(() -> {
            throw new ServiceException("Карта не найдена", HttpStatus.NOT_FOUND);
        });

        card.setHidden(request.getHidden());

        tinkoffCardRepository.save(card);

        return new TinkoffCardDTO(card);
    }
    public TinkoffAuthChromeTab startTinkoffConnect(TinkoffStartAuthRequest request) {
        User user = authHelper.getUserFromAuthCredentials();

//        RestTemplate restTemplate = new RestTemplate();
//        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
//
//        TinkoffAuthRequest authRequest = new TinkoffAuthRequest();
//
//        restTemplate.getMessageConverters().add(new ObjectToUrlEncodedMapper(mapper));
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        ResponseEntity<TinkoffSessionResponse> sessionResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/session?appName=pfphome&appVersion=pfphome-prod-v0.30.4&origin=web%2Cib5%2Cplatform",
//                HttpMethod.GET,
//                new HttpEntity<>(null, headers),
//                TinkoffSessionResponse.class);
//
//        if(sessionResponse.getStatusCodeValue() == 200) {
//            try {
//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
//                RequestBody body = RequestBody.create(mediaType, "phone=" + request.getPhone());
//                Request signRequest = new Request.Builder()
//                        .url("https://api.tinkoff.ru/v1/sign_up?sessionid=" + sessionResponse.getBody() + "&origin=web%2Cib5%2Cplatform")
//                        .method("POST", body)
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                        .build();
//                Response response = client.newCall(signRequest).execute();
//
//                String data = response.body().string();
//
//                TinkoffAuthResponse tinkoffAuthResponse = mapper.readValue(data, TinkoffAuthResponse.class);
//
//                authRequest.setPassword(request.getPassword());
//                authRequest.setPhone(request.getPhone());
//                authRequest.setOperationTicket(tinkoffAuthResponse.getOperationTicket());
//                authRequest.setInitialOperation(tinkoffAuthResponse.getInitialOperation());
//                authRequest.setSessionId(sessionResponse.getBody().getPayload());
//                authRequest.setReAuth(request.isReAuth());
//                authRequest.setStartDate(request.getExportStartDate());
//
//                authRequests.add(authRequest);
//
//                return authRequest;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }













//            System.out.println(sessionResponse.getBody().getPayload());
//            HashMap<String, Object> body = new HashMap<>();
//            body.put("phone", request.getPhone());
//
//            ResponseEntity<TinkoffAuthResponse> signUpResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/sign_up?sessionid=" + sessionResponse.getBody().getPayload() + "&origin=web,ib5,platform",
//                    HttpMethod.POST,
//                    new HttpEntity<>(body, headers),
//                    TinkoffAuthResponse.class);
//
//            System.out.println(signUpResponse.getStatusCodeValue());
//            System.out.println(signUpResponse.getBody());
//            System.out.println(signUpResponse.getBody().getOperationTicket());
//
//            authRequest.setPassword(request.getPassword());
//            authRequest.setPhone(request.getPhone());
//            authRequest.setOperationTicket(signUpResponse.getBody().getOperationTicket());
//            authRequest.setInitialOperation(signUpResponse.getBody().getInitialOperation());
//            authRequest.setSessionId(sessionResponse.getBody().getPayload());
//            authRequest.setReAuth(request.isReAuth());
//            authRequest.setStartDate(request.getExportStartDate());
//
//            authRequests.add(authRequest);
//
//            return authRequest;
        String phone = null;
        Instant startExportDate = null;
        String password = null;

        tinkoffChromeTabs.removeIf((val) -> val.getId().equals(user.getId()));

        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());

        if(request.isReAuth()) {
            if(integration.isEmpty())
                throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);

            phone = integration.get().getUsername();
            startExportDate = integration.get().getStartDate();
            password = integration.get().getPassword();
        } else {
            if (integration.isPresent())
                throw new ServiceException("Integration already exist", HttpStatus.BAD_REQUEST);

            if(request.getPhone() == null)
                throw new ServiceException("Pass phone to request body", HttpStatus.BAD_REQUEST);

            if(request.getExportStartDate() == null)
                throw new ServiceException("Pass exportStartDate to request body", HttpStatus.BAD_REQUEST);

            phone = request.getPhone();
            startExportDate = request.getExportStartDate();
        }

        WebDriver driver = WebDriverCreator.create();

        driver.get("https://www.tinkoff.ru/login/");

        WebElement phoneInput = new WebDriverWait(driver, Duration.of(60, ChronoUnit.SECONDS)).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("phoneNumber"))));
        phoneInput.sendKeys(phone);

        WebElement button = (new WebDriverWait(driver, Duration.of(60, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("submit-button"))));

        button.click();

        TinkoffAuthChromeTab tab = new TinkoffAuthChromeTab(driver);
        tab.setPhone(phone);
        tab.setUserId(user.getId());
        tab.setExportStartDate(startExportDate);
        tab.setPassword(password);

        this.tinkoffChromeTabs.add(tab);

        return tab;
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
            TinkoffAuthChromeTab tinkoffAuthChromeTab = null;

            for (TinkoffAuthChromeTab r : this.tinkoffChromeTabs)
                if(r.getId().equals(request.getId()))
                    tinkoffAuthChromeTab = r;

            if(tinkoffAuthChromeTab != null) {
                (new WebDriverWait(tinkoffAuthChromeTab.getDriver(), Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(tinkoffAuthChromeTab.getDriver().findElement(By.id("smsCode")))).sendKeys(request.getCode());

                (new WebDriverWait(tinkoffAuthChromeTab.getDriver(), Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(tinkoffAuthChromeTab.getDriver().findElement(By.id("password")))).sendKeys(tinkoffAuthChromeTab.getPassword() == null ?
                        request.getPassword() : tinkoffAuthChromeTab.getPassword());

                (new WebDriverWait(tinkoffAuthChromeTab.getDriver(), Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(tinkoffAuthChromeTab.getDriver().findElement(By.id("submit-button")))).click();

                System.out.println(tinkoffAuthChromeTab.getDriver().getCurrentUrl());
                System.out.println(tinkoffAuthChromeTab.getDriver().getPageSource());

                (new WebDriverWait(tinkoffAuthChromeTab.getDriver(), Duration.of(30, ChronoUnit.SECONDS))).until(ExpectedConditions.urlContains("summary"));

                if(!tinkoffAuthChromeTab.getDriver().getCurrentUrl().contains("summary")) {
                    tinkoffAuthChromeTab.getDriver().quit();

                    tinkoffChromeTabs.remove(tinkoffAuthChromeTab);

                    throw new ServiceException("Error on submit auth step, let`s try more or later", HttpStatus.INTERNAL_SERVER_ERROR);
                }

                Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(tinkoffAuthChromeTab.getUserId());

                if(integration.isPresent()) {
                    integration.get().setToken(tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue());

    //                if(integration.get().getStage().equals(TinkoffSyncStage.IN_SYNC))
    //                    return false;

                    tinkoffIntegrationRepository.save(integration.get());
                } else {
                    TinkoffIntegration newIntegration = new TinkoffIntegration(userService.getUserById(tinkoffAuthChromeTab.getUserId()),
                            tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue());

                    newIntegration.setPassword(request.getPassword());
                    newIntegration.setUsername(tinkoffAuthChromeTab.getPhone());
                    newIntegration.setStartDate(tinkoffAuthChromeTab.getExportStartDate());

                    tinkoffIntegrationRepository.save(newIntegration);
                }

                tinkoffAuthChromeTab.getDriver().quit();

                tinkoffChromeTabs.remove(tinkoffAuthChromeTab);

                return true;
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            tinkoffChromeTabs.removeIf((val) -> val.getId().equals(request.getId()));
            throw new ServiceException("Auth page element not found", HttpStatus.BAD_REQUEST);
        }

        throw new ServiceException("Start auth stage not found", HttpStatus.NOT_FOUND);
//        try {
//            User user = authHelper.getUserFromAuthCredentials();
//
//            TinkoffAuthRequest authRequest = null;
//
//            for (TinkoffAuthRequest r : this.authRequests)
//                if(r.getId().equals(request.getId()))
//                    authRequest = r;
//
//            if(authRequest != null) {
////                Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());
//
//                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
//
//                try {
//                    OkHttpClient client = new OkHttpClient().newBuilder()
//                            .build();
//                    okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
//                    RequestBody body = RequestBody.create(mediaType, "initialOperation=sign_up&initialOperationTicket=" + authRequest.getOperationTicket() + "&confirmationData={\"SMSBYID\": " + Integer.parseInt(request.getCode()) + "}");
//                    Request confirmRequest = new Request.Builder()
//                            .url("https://api.tinkoff.ru/v1/confirm?sessionid=" + authRequest.getSessionId() + "&origin=web%2Cib5%2Cplatform")
//                            .method("POST", body)
//                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                            .build();
//                    Response response = client.newCall(confirmRequest).execute();
//
//                    System.out.println(response.body().string());
//
//                    return true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                RestTemplate restTemplate = new RestTemplate();
//
//                restTemplate.getMessageConverters().add(new ObjectToUrlEncodedMapper(mapper));
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//                HashMap<String, Object> body = new HashMap<>();
//                body.put("initialOperation", "sign_up");
//                body.put("initialOperationTicket", authRequest.getOperationTicket());
//
//                try {
//                    body.put("confirmationData", mapper.writeValueAsString(new TinkoffSmsDataRequest(Integer.parseInt(request.getCode()))));
//                    System.out.println(mapper.writeValueAsString(new TinkoffSmsDataRequest(Integer.parseInt(request.getCode()))));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(authRequest.getPassword());
//                System.out.println(authRequest.getPhone());
//                System.out.println(authRequest.getOperationTicket());
//                System.out.println(authRequest.getSessionId());
//                TinkoffSmsSubmitRequest smsSubmitRequest = new TinkoffSmsSubmitRequest();
//
//                smsSubmitRequest.setInitialOperation("sign_up");
//                smsSubmitRequest.setInitialOperationTicket(authRequest.getOperationTicket());
//                smsSubmitRequest.setConfirmationData("{\"SMSBYID\":" + Integer.parseInt(request.getCode()) + "}");
//
//                HttpEntity requestData = new HttpEntity<>(body, headers);
//
//                ResponseEntity<String> signUpResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/confirm?sessionid=" + authRequest.getSessionId() + "&origin=web%2Cib5%2Cplatform",
//                        HttpMethod.POST,
//                        requestData,
//                        String.class);
//
//                System.out.println(signUpResponse.getBody());
//
//                if(signUpResponse.getStatusCodeValue() == 200 && !signUpResponse.getBody().contains("error")) {
//                    HashMap<String, Object> submitBody = new HashMap<>();
//                    body.put("username", authRequest.getPhone());
//                    body.put("password", authRequest.getPassword());
//
//                    ResponseEntity<TinkoffAuthResponse> submitResponse = restTemplate.exchange("https://api.tinkoff.ru/v1/sign_up?sessionid=" + authRequest.getSessionId() + "&origin=web,ib5,platform",
//                            HttpMethod.POST,
//                            new HttpEntity<>(submitBody, headers),
//                            TinkoffAuthResponse.class);
//
//                    ResponseEntity<String> levelUp = restTemplate.exchange("https://api.tinkoff.ru/v1/level_up?sessionid=" + authRequest.getSessionId() + "&origin=web%2Cib5%2Cplatform",
//                            HttpMethod.POST,
//                            new HttpEntity<>(null, null),
//                            String.class);
//
//                    if(levelUp.getStatusCodeValue() == 200 && !levelUp.getBody().contains("error")) {
//                        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(user.getId());
//
//                        if(integration.isPresent()) {
//                            integration.get().setToken(authRequest.getSessionId());
//
//                            tinkoffIntegrationRepository.save(integration.get());
//                        } else {
//                            TinkoffIntegration newIntegration = new TinkoffIntegration(user,
//                                    authRequest.getSessionId());
//
//                            newIntegration.setPassword(request.getPassword());
//                            newIntegration.setUsername(authRequest.getPhone());
//                            newIntegration.setStartDate(authRequest.getStartDate());
//
//                            tinkoffIntegrationRepository.save(newIntegration);
//                        }
//
//                        return true;
//                    }
//
//                    return false;
//                }

//                return false;
//            }
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//            tinkoffChromeTabs.removeIf((val) -> val.getId().equals(request.getId()));
//            throw new ServiceException("Auth page element not found", HttpStatus.BAD_REQUEST);
//        }

//        throw new ServiceException("Start auth stage not found", HttpStatus.NOT_FOUND);
    }
}
