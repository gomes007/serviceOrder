package com.softwarehouse.serviceorder.security.service;

import com.softwarehouse.serviceorder.security.domain.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;


    public AuthenticationService(final UserService userService, final TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public String authenticate(final String login, final String encodedPassword) {
        final User found = this.userService.findUserByLogin(login);
        this.userService.passwordMatches(
                new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8),
                found
        );

        return this.tokenService.generateToken(found);
    }
}
