package com.wp.system.response;

import java.util.List;

public class PagingResponse<T> {
    private List<T> page;

    private int total;

    public PagingResponse() {}

    public PagingResponse(List<T> page, int total) {
        this.page = page;
        this.total = total;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
