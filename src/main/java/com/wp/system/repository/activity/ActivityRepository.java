package com.wp.system.repository.activity;

import com.wp.system.entity.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM activity WHERE user_id = ?1 AND start_time BETWEEN ?2 AND ?3")
    List<Activity> getUserActivityByPeriod(UUID userId, Timestamp start, Timestamp end);
}
