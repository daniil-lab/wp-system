package com.wp.system.utils;

public interface SmsSender {
    boolean send();

    void setPhone(String phone);

    void setContent(String content);
}
