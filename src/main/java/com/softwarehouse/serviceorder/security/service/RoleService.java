package com.softwarehouse.serviceorder.security.service;

import com.softwarehouse.serviceorder.security.domain.Role;
import com.softwarehouse.serviceorder.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String SYSTEM_ROLE = "EMPLOYEE";

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getAdminRole() {
        return this.repository.findByName(ADMIN_ROLE).orElseGet(() -> {
            final Role adminRole = new Role();
            adminRole.setName(ADMIN_ROLE);

            return this.repository.save(adminRole);
        });
    }

    public Role getSystemRole() {
        return this.repository.findByName(SYSTEM_ROLE).orElseGet(() -> {
            final Role adminRole = new Role();
            adminRole.setName(SYSTEM_ROLE);

            return this.repository.save(adminRole);
        });
    }
}
