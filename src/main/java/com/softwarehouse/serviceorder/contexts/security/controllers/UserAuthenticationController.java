package com.softwarehouse.serviceorder.contexts.security.controllers;

import com.softwarehouse.serviceorder.contexts.security.models.UserAuthenticationRequest;
import com.softwarehouse.serviceorder.contexts.security.models.UserAuthenticationResponse;
import com.softwarehouse.serviceorder.contexts.security.services.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User Authentication and Authorization")
@RequestMapping("/users")
public class UserAuthenticationController {
    private final AuthenticationService authenticationService;

    public UserAuthenticationController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @SecurityRequirements
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody UserAuthenticationRequest request) {
        return this.authenticationService.authenticate(request.getLogin(), request.getPassword());
    }

    @PostMapping("/validate")
    public UserAuthenticationResponse validateToken(Authentication user) {
        return UserAuthenticationResponse
                .builder()
                .login((String) user.getPrincipal())
                .roles(user.getAuthorities().stream().map(ga -> new SimpleGrantedAuthority(ga.getAuthority())).toList())
                .build();
    }
}
