package com.wp.system.services.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffCard;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.tinkoff.TinkoffCardRepository;
import com.wp.system.request.tinkoff.TinkoffStartAuthRequest;
import com.wp.system.request.tinkoff.TinkoffSubmitAuthRequest;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.tinkoff.TinkoffSync;
import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URL;
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

    @Scheduled(fixedDelay = 900 * 100)
    public void cleanTabs() {
        tinkoffChromeTabs.removeIf(tab -> tab.getExpiredAt().isBefore(Instant.now()));
    }

    public TinkoffSyncStage getSyncStage(UUID userId) {
        TinkoffIntegration integration = tinkoffIntegrationRepository.findById(userId).orElseThrow(() -> {
            throw new ServiceException("Integration not found", HttpStatus.NOT_FOUND);
        });

        return integration.getStage();
    }

    public TinkoffAuthChromeTab startTinkoffAuth(TinkoffStartAuthRequest request) {
        userService.getUserById(request.getUserId());

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 1078);
        deviceMetrics.put("height", 924);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 8.0.0;" +
                        "Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML," +
                "like Gecko) " +
        "Chrome/67.0.3396.99 Mobile Safari/537.36");

        ChromeOptions option = new ChromeOptions();

        option.setExperimentalOption("mobileEmulation", mobileEmulation);
        option.addArguments("--disable-blink-features=AutomationControlled");
        option.addArguments("--headless");
        option.addArguments("--disable-gpu");
        option.setCapability("chrome.switches", Arrays.asList("--proxy-server=http://robocontext:34LAFVWNUC@ru3.mproxy.top:20004"));

        URL url = null;

        try {
            url = new URL(System.getenv("SELENIUM_URL"));
        } catch (Exception e) {
            return null;
        }

        WebDriver driver = new RemoteWebDriver(url, option);

        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        driver.get("https://www.tinkoff.ru/login/");

        WebElement phoneInput = driver.findElement(By.id("phoneNumber"));
        phoneInput.sendKeys(request.getPhone());

        WebElement button = driver.findElement(By.id("submit-button"));

        button.click();

        TinkoffAuthChromeTab tab = new TinkoffAuthChromeTab(driver);
        tab.setUserId(request.getUserId());

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

    public Boolean submitTinkoffAuth(TinkoffSubmitAuthRequest request) {
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

                if(integration.get().getCards() == null)
                    integration.get().setCards(new HashSet<>());

                if(integration.get().getStage().equals(TinkoffSyncStage.IN_SYNC))
                    return false;

                tinkoffIntegrationRepository.save(integration.get());
            } else {
                tinkoffIntegrationRepository.save(new TinkoffIntegration(userService.getUserById(tinkoffAuthChromeTab.getUserId()),
                        tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("api_session").getValue(),
                        tinkoffAuthChromeTab.getDriver().manage().getCookieNamed("__P__wuid").getValue()));
            }

            tinkoffAuthChromeTab.getDriver().quit();

            tinkoffChromeTabs.remove(tinkoffAuthChromeTab);

            return true;
        }

        throw new ServiceException("Start auth stage not found", HttpStatus.NOT_FOUND);
    }
}
