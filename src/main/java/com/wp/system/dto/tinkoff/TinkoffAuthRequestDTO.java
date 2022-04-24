package com.wp.system.dto.tinkoff;

import com.wp.system.utils.tinkoff.TinkoffAuthChromeTab;
import com.wp.system.utils.tinkoff.TinkoffAuthRequest;
import com.wp.system.utils.tinkoff.request.TinkoffSmsRequest;

import java.util.UUID;

public class TinkoffAuthRequestDTO {
    private UUID id;

    public TinkoffAuthRequestDTO() {}

    public TinkoffAuthRequestDTO(TinkoffAuthRequest r) {
        if(r == null)
            return;

        this.id = r.getId();
    }

    public TinkoffAuthRequestDTO(TinkoffAuthChromeTab t) {
        if(t == null)
            return;

        this.id = t.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
