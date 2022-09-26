package com.wp.system.repository.category;

import com.wp.system.entity.category.BaseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BaseCategoryRepository extends JpaRepository<BaseCategory, UUID> {
}
