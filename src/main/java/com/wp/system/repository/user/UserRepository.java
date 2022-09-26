package com.wp.system.repository.user;

import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u FROM User u JOIN u.email e WHERE e.address = ?1")
    Optional<User> findByEmail(String email);
}
