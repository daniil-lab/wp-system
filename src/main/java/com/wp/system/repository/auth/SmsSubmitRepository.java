package com.wp.system.repository.auth;

import com.wp.system.entity.auth.SmsSubmit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SmsSubmitRepository extends CrudRepository<SmsSubmit, UUID> {
}
