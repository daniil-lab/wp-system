package com.wp.system.services.tinkoff;

import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.tinkoff.TinkoffIntegrationRepository;
import com.wp.system.response.tinkoff.TinkoffAuthValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class TinkoffService {

    @Autowired
    private TinkoffIntegrationRepository tinkoffIntegrationRepository;

//    public TinkoffAuthValidateResponse validateAuthRequest() {
//
//    }

    public TinkoffIntegration getUserTinkoffIntegration(UUID id) {
        return tinkoffIntegrationRepository.getTinkoffIntegrationByUserId(id).orElseThrow(() -> {
            throw new ServiceException("Tinkoff integration not found", HttpStatus.NOT_FOUND);
        });
    }

    public TinkoffIntegration getTinkoffIntegrationById(UUID id) {
        return tinkoffIntegrationRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Tinkoff integration not found", HttpStatus.NOT_FOUND);
        });
    }
}
