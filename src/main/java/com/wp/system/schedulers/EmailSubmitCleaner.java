package com.wp.system.schedulers;

import com.wp.system.entity.email.EmailSubmitRequest;
import com.wp.system.repository.email.EmailSubmitRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
public class EmailSubmitCleaner {

    @Autowired
    private EmailSubmitRequestRepository emailSubmitRequestRepository;

    @Scheduled(fixedRate = 60000 * 60)
    public void clean() {
        List<EmailSubmitRequest> requests = emailSubmitRequestRepository.getExpirationRequests(Timestamp.from(Instant.now()));

        requests.forEach(emailSubmitRequestRepository::delete);
    }
}
