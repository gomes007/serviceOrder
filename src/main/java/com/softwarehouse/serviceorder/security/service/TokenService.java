package com.softwarehouse.serviceorder.security.service;

import com.auth0.jwt.JWT;
import com.softwarehouse.serviceorder.security.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String generateToken(final User user) {
        final Date expiresAt = Date.from(LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.UTC));

        return JWT
                .create()
                .withClaim("login", user.getLogin())
                .withClaim("roles", user.getStringRoles())
                .withExpiresAt(expiresAt)
                .sign(JwtSignToken.ALGO);
    }

}
