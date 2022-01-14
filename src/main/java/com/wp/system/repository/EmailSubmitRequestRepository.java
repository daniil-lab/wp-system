package com.wp.system.repository;

import com.wp.system.entity.EmailSubmitRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailSubmitRequestRepository extends JpaRepository<EmailSubmitRequest, UUID> {
}
