package com.wp.system.repository.user;

import com.wp.system.entity.user.UserRolePermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRolePermissionRepository extends CrudRepository<UserRolePermission, UUID> {
    @Query("SELECT p FROM UserRolePermission p JOIN p.role r WHERE r.id = ?1")
    List<UserRolePermission> getAllPermissionsInRole(UUID roleId);
}
