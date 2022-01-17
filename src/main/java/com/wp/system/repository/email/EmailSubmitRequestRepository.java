package com.wp.system.repository.email;

import com.wp.system.entity.email.EmailSubmitRequest;
import com.wp.system.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailSubmitRequestRepository extends JpaRepository<EmailSubmitRequest, UUID> {
    Optional<EmailSubmitRequest> findByUserId(UUID userId);

    @Query(nativeQuery = true, value = "SELECT * FROM email_submit_request WHERE expiration < ?1")
    List<EmailSubmitRequest> getExpirationRequests(Timestamp timestamp);
}
