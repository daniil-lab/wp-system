package com.wp.system.schedulers;

import com.wp.system.entity.auth.PhoneAuthData;
import com.wp.system.entity.auth.SmsSubmit;
import com.wp.system.repository.auth.PhoneAuthRequestRepository;
import com.wp.system.repository.auth.SmsSubmitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SmsSubmitCleaner {
    @Autowired
    private SmsSubmitRepository smsSubmitRepository;

    @Scheduled(fixedRate = 60000 * 60)
    public void clean() {
        Iterable<SmsSubmit> data = smsSubmitRepository.findAll();

        for(SmsSubmit smsSubmit : data) {
            if(Instant.parse(smsSubmit.getCreateAt()).plusSeconds(60 * 30).isBefore(Instant.now())) {
                smsSubmitRepository.delete(smsSubmit);
            }
        }
    }
}
