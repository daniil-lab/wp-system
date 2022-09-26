package com.wp.system.repository.email;

import com.wp.system.entity.email.EmailMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailMailRepository extends JpaRepository<EmailMail, UUID> {
}
