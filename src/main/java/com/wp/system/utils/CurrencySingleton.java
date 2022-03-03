package com.wp.system.utils;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class CurrencySingleton {
    private List<CurrencySingletonCourse> courseList = new ArrayList<>();

    private CurrencyLayerAdapter currencyLayerAdapter;

    private boolean error = true;

    @Scheduled(timeUnit = TimeUnit.DAYS, fixedDelay = 1)
    public void pullData() {
        Map<String, Object> data = currencyLayerAdapter.getAllCurses();

        if(data == null)
            return;

        for (var entry : data.entrySet())
            this.courseList.add(new CurrencySingletonCourse(entry.getKey().substring(3), Double.parseDouble(
                    new DecimalFormat("#.##").format(Double.valueOf(entry.getValue().toString())).replace(",", ".")
            )));

        this.error = false;
    }

    public CurrencySingleton(CurrencyLayerAdapter currencyLayerAdapter) {
        this.currencyLayerAdapter = currencyLayerAdapter;
    };

    public List<CurrencySingletonCourse> getCourseList() {
        return courseList;
    }

    public CurrencySingletonCourse findCourse(WalletType type) {
        for (CurrencySingletonCourse course : courseList) {
            if(course.getWallet().equals(type.name()))
                return course;
        }

        return null;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
