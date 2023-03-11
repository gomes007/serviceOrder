package com.softwarehouse.serviceorder.contexts.security.repositories;

import com.softwarehouse.serviceorder.contexts.security.entities.Role;
import com.softwarehouse.serviceorder.contexts.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findFirstByRolesName(final String roleName);
}
