package com.softwarehouse.serviceorder.security.service;

import com.softwarehouse.serviceorder.exceptions.impl.UnauthorizedException;
import com.softwarehouse.serviceorder.security.domain.User;
import com.softwarehouse.serviceorder.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository repository, final PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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
}
