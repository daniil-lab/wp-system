package com.wp.system.utils;

public class CurrencySingletonCourse {

    private String wallet;

    private Double course;

    public CurrencySingletonCourse () {};

    public CurrencySingletonCourse(String wallet, Double course) {
        this.wallet = wallet;
        this.course = course;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }
}
