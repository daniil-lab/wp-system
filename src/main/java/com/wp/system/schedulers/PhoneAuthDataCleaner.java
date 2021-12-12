package com.wp.system.schedulers;

import com.wp.system.entity.auth.PhoneAuthData;
import com.wp.system.repository.auth.PhoneAuthRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;

@Component
public class PhoneAuthDataCleaner {

    @Autowired
    private PhoneAuthRequestRepository phoneAuthRequestRepository;

    @Scheduled(fixedRate = 60000 * 60)
    public void cleanData() {
        Iterable<PhoneAuthData> data = phoneAuthRequestRepository.findAll();

        for(PhoneAuthData phoneAuthData : data) {
            if(Instant.parse(phoneAuthData.getCreateAt()).plusSeconds(60 * 30).isBefore(Instant.now())) {
                phoneAuthRequestRepository.delete(phoneAuthData);
           }
        }
    }
}
