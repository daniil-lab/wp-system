package com.wp.system.other;

public interface SmsSender {
    boolean send();

    void setPhone(String phone);

    void setContent(String content);
}
