package com.wp.system.repository.user;

import com.wp.system.entity.user.UserRolePermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRolePermissionRepository extends CrudRepository<UserRolePermission, UUID> {
}
