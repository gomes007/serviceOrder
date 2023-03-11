package com.softwarehouse.serviceorder.contexts.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.softwarehouse.serviceorder.contexts.security.services.JwtSignToken;
import com.softwarehouse.serviceorder.contexts.security.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class JwtTokenFilter extends BasicAuthenticationFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserService userService;

    public JwtTokenFilter(AuthenticationManager authenticationManager, final UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String authToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(authToken) || !authToken.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = authToken.replaceAll(TOKEN_PREFIX, "");

        DecodedJWT decoded = JWT.require(JwtSignToken.ALGO).build().verify(token);

        LocalDateTime expiresAt = LocalDateTime.ofInstant(
                decoded.getExpiresAtAsInstant(),
                ZoneOffset.UTC
        );

        if (expiresAt.isBefore(LocalDateTime.now())) {
            chain.doFilter(request, response);
            return;
        }

        final String login = decoded.getClaim("login").asString();

        this.userService.userExists(login);

        final List<String> roles = decoded.getClaim("roles").asList(String.class);

        if (roles.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        final List<SimpleGrantedAuthority> authorities = roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        final UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(
                login,
                null,
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(principal);

        chain.doFilter(request, response);
    }
}
