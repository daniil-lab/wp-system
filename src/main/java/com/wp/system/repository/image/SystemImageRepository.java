package com.wp.system.repository.image;

import com.wp.system.entity.image.SystemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SystemImageRepository extends JpaRepository<SystemImage, UUID> {
}
