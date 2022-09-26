package com.wp.system.repository.auth;

import com.wp.system.entity.auth.PhoneAuthData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneAuthRequestRepository extends CrudRepository<PhoneAuthData, UUID> {
}
