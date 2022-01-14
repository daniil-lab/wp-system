package com.wp.system.repository.logging;

import com.wp.system.entity.logging.SystemAdminLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SystemAdminLogRepository extends JpaRepository<SystemAdminLog, UUID> {
}
