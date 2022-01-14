package com.wp.system.services;

import com.wp.system.repository.EmailSubmitRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSubmitService {
    @Autowired
    private EmailSubmitRequestRepository emailSubmitRequestRepository;
}
