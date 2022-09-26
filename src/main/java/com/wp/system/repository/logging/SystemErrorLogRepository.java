package com.wp.system.repository.logging;

import com.wp.system.entity.logging.SystemErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface SystemErrorLogRepository extends JpaRepository<SystemErrorLog, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM article WHERE publish_date BETWEEN ?1 AND ?2")
    List<SystemErrorLog> getLogsByPeriod(Timestamp startDate, Timestamp endDate);
}
