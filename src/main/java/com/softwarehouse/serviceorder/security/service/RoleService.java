package com.softwarehouse.serviceorder.security.service;

import com.softwarehouse.serviceorder.security.domain.Role;
import com.softwarehouse.serviceorder.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getAdminRole() {
        return this.repository.findByName("ADMIN").orElseGet(() -> {
            final Role adminRole = new Role();
            adminRole.setName("ADMIN");

            return this.repository.save(adminRole);
        });
    }
}
