package com.wp.system.services.sber;

import com.wp.system.entity.sber.SberIntegration;
import com.wp.system.entity.tinkoff.TinkoffIntegration;
import com.wp.system.entity.tinkoff.TinkoffSyncStage;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.sber.SberIntegrationRepository;
import com.wp.system.request.sber.CreateSberIntegrationRequest;
import com.wp.system.request.sber.SubmitCreateSberIntegrationRequest;
import com.wp.system.services.user.UserService;
import com.wp.system.utils.sber.SberAuth;
import com.wp.system.utils.sber.SberRegister;
import com.wp.system.utils.sber.SberSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SberService {
    @Autowired
    private SberIntegrationRepository sberIntegrationRepository;

    @Autowired
    private UserService userService;

    private List<SberRegister> registerList = new ArrayList<>();

    public boolean startCreateSberIntegration(CreateSberIntegrationRequest request) {
        registerList.removeIf(register -> register.getUserId().equals(request.getUserId()));

        SberRegister sberRegister = new SberRegister(request.getUserId(), request.getPhone());

        sberRegister.setStartExportDate(request.getStartExportDate());
        sberRegister.register();

        registerList.add(sberRegister);

        return true;
    }

    public SberIntegration submitCreateSberIntegration(SubmitCreateSberIntegrationRequest request) {
        SberRegister sberRegister = null;

        for (SberRegister r : registerList)
            if(r.getUserId().equals(request.getUserId())) {
                sberRegister = r;
                break;
            }

        if(sberRegister == null)
            throw new ServiceException("Register session not found", HttpStatus.BAD_REQUEST);

        sberRegister.submitRegister(request.getCode());
        sberRegister.createPin();

        SberIntegration integration = new SberIntegration();
        integration.setUser(userService.getUserById(sberRegister.getUserId()));
        integration.setToken(sberRegister.getToken());
        integration.setHost(sberRegister.getHost());
        integration.setmGUID(sberRegister.getmGUID());
        integration.setSession(sberRegister.getjSession());
        integration.setStartDate(sberRegister.getStartExportDate());

        sberIntegrationRepository.save(integration);

        return integration;
    }

    public SberIntegration getSberIntegrationByUserId(UUID id) {
        return sberIntegrationRepository.getSberIntegrationByUserId(id).orElseThrow(() -> {
            throw new ServiceException("Sber integration not found", HttpStatus.BAD_REQUEST);
        });
    }

    public boolean syncSber(UUID userId) {
        SberAuth sberAuth = new SberAuth(sberIntegrationRepository, getSberIntegrationByUserId(userId));

        sberAuth.auth();

        SberSync sberSync = new SberSync(getSberIntegrationByUserId(userId), sberIntegrationRepository);

        sberSync.sync();

        return true;
    }

    @Scheduled(fixedDelay = 900 * 100)
    public void cleanTabs() {
        registerList.removeIf(register -> register.getCreateAt().isBefore(Instant.now()));
    }
}
