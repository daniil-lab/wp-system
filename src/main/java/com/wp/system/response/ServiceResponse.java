package com.wp.system.response;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponse<T> {
    private int status;

    private T data;

    private String message;

    private List<String> advices = new ArrayList<>();

    public ServiceResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServiceResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ServiceResponse(int status, T data, String message, List<String> advices) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.advices = advices;
    }

    public ServiceResponse(int status, T data, List<String> advices) {
        this.status = status;
        this.data = data;
        this.advices = advices;
    }

    public void addAdvice(String advice) {
        this.advices.add(advice);
    }

    public void removeAdvice(String advice) {
        this.advices.remove(advice);
    }

    public void remoteAdvice(int idx) {
        this.advices.remove(idx);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAdvices() {
        return advices;
    }

    public void setAdvices(List<String> advices) {
        this.advices = advices;
    }
}
