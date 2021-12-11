package com.wp.system.repository.category;

import com.wp.system.entity.category.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {
    @Query("SELECT c FROM Category c JOIN c.user u WHERE u.id = ?1")
    List<Category> getAllUserCategories(UUID userId);
}
