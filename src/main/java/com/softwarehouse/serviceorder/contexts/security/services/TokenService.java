package com.softwarehouse.serviceorder.contexts.security.services;

import com.auth0.jwt.JWT;
import com.softwarehouse.serviceorder.contexts.security.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class TokenService {
    private static final Integer AUTH_TOKEN_EXPIRATION_HOURS = 12;
    private static final Integer REGISTRATION_TOKEN_EXPIRATION_HOURS = 1;

    public String generateToken(final User user) {
        final Date expiresAt = Date.from(LocalDateTime.now().plusHours(AUTH_TOKEN_EXPIRATION_HOURS).toInstant(ZoneOffset.UTC));

        return JWT
                .create()
                .withClaim("login", user.getLogin())
                .withClaim("roles", user.getStringRoles())
                .withExpiresAt(expiresAt)
                .sign(JwtSignToken.ALGO);
    }

    public String generateFirstAccessToken(final User user) {
        final Date expiresAt = Date.from(LocalDateTime.now().plusHours(REGISTRATION_TOKEN_EXPIRATION_HOURS).toInstant(ZoneOffset.UTC));

        return JWT
                .create()
                .withClaim("login", user.getLogin())
                .withExpiresAt(expiresAt)
                .sign(JwtSignToken.ALGO);
    }
}
