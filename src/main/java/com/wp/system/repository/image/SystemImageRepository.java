package com.wp.system.repository.image;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.utils.SystemImageTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SystemImageRepository extends JpaRepository<SystemImage, UUID> {
    List<SystemImage> findByTag(SystemImageTag tag);
}
