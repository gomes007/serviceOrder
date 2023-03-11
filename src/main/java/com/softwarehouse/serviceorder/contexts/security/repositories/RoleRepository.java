package com.softwarehouse.serviceorder.contexts.security.repositories;

import com.softwarehouse.serviceorder.contexts.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(final String name);
}
