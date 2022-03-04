package com.wp.system.services.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.entity.tinkoff.TinkoffTransaction;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.tinkoff.TinkoffSync;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.utils.tinkoff.WebDriverCreator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TinkoffService {

    @Autowired
    private TinkoffIntegrationRepository tinkoffIntegrationRepository;

    @Autowired
    private TinkoffCardRepository tinkoffCardRepository;

    @Autowired
    private UserService userService;

    private List<TinkoffAuthChromeTab> tinkoffChromeTabs = new ArrayList<>();

    public TinkoffIntegration getIntegrationByUserId(UUID userId) {
        return tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });
    }

    public Set<TinkoffTransaction> getTransactionsByCardId(UUID cardId) {
        return tinkoffCardRepository.findById(cardId).orElseThrow(() -> {
            throw new ServiceException("Card not found", HttpStatus.NOT_FOUND);
        }).getTransactions();
    }

    @Transactional
    public TinkoffIntegration removeIntegration(UUID userId) {
        TinkoffIntegration integration = getIntegrationByUserId(userId);

        tinkoffIntegrationRepository.delete(integration);

        return integration;
    }

    @Scheduled(fixedDelay = 900 * 100)
    public void cleanTabs() {
        tinkoffChromeTabs.removeIf(tab -> tab.getExpiredAt().isBefore(Instant.now()));
    }

    public TinkoffSyncStage getSyncStage(UUID userId) {
        TinkoffIntegration integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        return integration.getStage();
    }

    public TinkoffAuthChromeTab startTinkoffConnect(TinkoffStartAuthRequest request) {
        userService.getUserById(request.getUserId());

        String phone = null;
        UUID userId = null;

        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(request.getUserId());

        if(request.isReAuth()) {
            if(integration.isEmpty())
                throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);

            phone = integration.get().getUsername();
            userId = integration.get().getUser().getId();
        } else {
            if (integration.isPresent())
                throw new ServiceException("Integration already exist", HttpStatus.BAD_REQUEST);

            if(request.getPhone() == null)
                throw new ServiceException("Pass phone to request body", HttpStatus.BAD_REQUEST);

            if(request.getUserId() == null)
                throw new ServiceException("Pass userId to request body", HttpStatus.BAD_REQUEST);

            if(request.getExportStartDate() == null)
                throw new ServiceException("Pass exportStartDate to request body", HttpStatus.BAD_REQUEST);

            phone = request.getPhone();
            userId = request.getUserId();
        }
        WebDriver driver = WebDriverCreator.create();

        driver.get("https://www.tinkoff.ru/login/");

        WebElement phoneInput = driver.findElement(By.id("phoneNumber"));
        phoneInput.sendKeys(phone);

        WebElement button = driver.findElement(By.id("submit-button"));

        button.click();

        TinkoffAuthChromeTab tab = new TinkoffAuthChromeTab(driver);
        tab.setPhone(phone);
        tab.setUserId(userId);
        tab.setExportStartDate(request.getExportStartDate());

        this.tinkoffChromeTabs.add(tab);

        return tab;
    }

    public Set<TinkoffCard> getCards(UUID userId) {
        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(userId);

        if(integration.isPresent())
            return integration.get().getCards();

        throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public Boolean sync(UUID userId) {
        Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(userId);

        if(integration.isPresent()) {
            integration.get().setStage(TinkoffSyncStage.IN_SYNC);

            TinkoffSync tinkoffSync = new TinkoffSync(integration.get());
            tinkoffSync.setCardRepository(tinkoffCardRepository);
            tinkoffSync.setIntegrationRepository(tinkoffIntegrationRepository);

            if(integration.get().getStage().equals(TinkoffSyncStage.IN_SYNC))
                return false;

            new Thread(tinkoffSync::sync).start();

            return true;
        }

        throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
    }

    public Boolean submitTinkoffConnect(TinkoffSubmitAuthRequest request) {
        TinkoffAuthChromeTab tinkoffAuthChromeTab = null;

        for (TinkoffAuthChromeTab r : this.tinkoffChromeTabs)
            if(r.getId().equals(request.getId()))
                tinkoffAuthChromeTab = r;

        if(tinkoffAuthChromeTab != null) {
            tinkoffAuthChromeTab.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            tinkoffAuthChromeTab.getDriver().findElement(By.id("smsCode")).sendKeys(request.getCode());

            tinkoffAuthChromeTab.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            tinkoffAuthChromeTab.getDriver().findElement(By.id("password")).sendKeys(request.getPassword());

            tinkoffAuthChromeTab.getDriver().findElement(By.id("submit-button")).click();

            Optional<TinkoffIntegration> integration = tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(tinkoffAuthChromeTab.getUserId());

            if(integration.isPresent()) {
                integration.get().setToken(tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue());
                integration.get().setWuid(tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("__P__wuid").getValue());

                if(integration.get().getStage().equals(TinkoffSyncStage.IN_SYNC))
                    return false;

                tinkoffIntegrationRepository.save(integration.get());
            } else {
                TinkoffIntegration newIntegration = new TinkoffIntegration(userService.getUserById(tinkoffAuthChromeTab.getUserId()),
                        tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue(),
                        tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("__P__wuid").getValue());

                newIntegration.setPassword(request.getPassword());
                newIntegration.setUsername(tinkoffAuthChromeTab.getPhone());
                newIntegration.setStartDate(tinkoffAuthChromeTab.getExportStartDate());

                tinkoffIntegrationRepository.save(newIntegration);
            }

            tinkoffAuthChromeTab.getDriver().quit();

            tinkoffChromeTabs.remove(tinkoffAuthChromeTab);

            return true;
        }

        throw new ServiceException("Start auth stage not found", HttpStatus.NOT_FOUND);
    }
}
