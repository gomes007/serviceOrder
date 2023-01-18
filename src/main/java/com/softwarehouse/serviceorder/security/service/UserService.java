package com.softwarehouse.serviceorder.security.service;

import com.softwarehouse.serviceorder.exceptions.impl.UnauthorizedException;
import com.softwarehouse.serviceorder.security.domain.User;
import com.softwarehouse.serviceorder.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public UserService(final UserRepository repository,
                       final PasswordEncoder passwordEncoder,
                       final RoleService roleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User findUserByLogin(final String login) {
        return this.repository.findById(login).orElseThrow(() -> new UnauthorizedException("login or password not matches"));
    }

    public boolean userExists(final String login) {
        return this.findUserByLogin(login) != null;
    }

    public void passwordMatches(final String rawPassword, final User loggingUser) {
        if (!this.passwordEncoder.matches(rawPassword, loggingUser.getPassword()))
            throw new UnauthorizedException("login or password not matches");
    }

    public void createDefaultUser() {
        if (this.repository.count() <= 0) {
            final User user = new User();

            user.setLogin("admin");
            user.setPassword(this.passwordEncoder.encode("admin"));
            user.setRoles(List.of(this.roleService.getAdminRole()));
            user.setExternalReference(-1L);

            this.repository.save(user);
        }
    }
}
