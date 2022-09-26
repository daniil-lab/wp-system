package com.wp.system.response;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagingResponse<T> {
    private List<T> page;

    private long total;

    private Integer totalPages;

    public PagingResponse() {}

    public PagingResponse(Page<T> p) {
        this.page = p.getContent();
        this.total = p.getTotalElements();
        this.totalPages = p.getTotalPages();
    }

    public PagingResponse(List<T> page, int total) {
        this.page = page;
        this.total = total;
    }

    public PagingResponse(List<T> page, long total) {
        this.page = page;
        this.total = total;
    }

    public PagingResponse(List<T> page, int total, int totalPages) {
        this.page = page;
        this.total = total;
        this.totalPages = totalPages;
    }

    public PagingResponse(List<T> page, long total, int totalPages) {
        this.page = page;
        this.total = total;
        this.totalPages = totalPages;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
