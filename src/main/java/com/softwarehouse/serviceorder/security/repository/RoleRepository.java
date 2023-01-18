package com.softwarehouse.serviceorder.security.repository;

import com.softwarehouse.serviceorder.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(final String name);
}
