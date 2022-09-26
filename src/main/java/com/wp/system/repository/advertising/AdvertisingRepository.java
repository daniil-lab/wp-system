package com.wp.system.repository.advertising;

import com.wp.system.entity.advertising.Advertising;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdvertisingRepository extends JpaRepository<Advertising, UUID> {
}
