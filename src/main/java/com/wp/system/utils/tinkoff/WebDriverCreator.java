package com.wp.system.utils.tinkoff;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class WebDriverCreator {
    public static WebDriver create() {
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
//        option.setCapability("chrome.switches", Arrays.asList("--proxy-server=http://robocontext:34LAFVWNUC@ru3.mproxy.top:20004"));

        URL url = null;

        try {
            url = new URL(System.getenv("SELENIUM_URL"));
        } catch (Exception e) {
            return null;
        }

        WebDriver driver = new RemoteWebDriver(url, option);

        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        return driver;
    }
}
