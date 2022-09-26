package com.wp.system.repository.user;

import com.wp.system.entity.user.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {
    Optional<UserRole> findByName(String name);

    Optional<UserRole> findByAutoApply(boolean apply);

    Optional<UserRole> findByRoleAfterBuy(boolean buy);

    Optional<UserRole> findByRoleAfterBuyExpiration(boolean buy);

    Optional<UserRole> findByRoleForBlocked(boolean val);
}
