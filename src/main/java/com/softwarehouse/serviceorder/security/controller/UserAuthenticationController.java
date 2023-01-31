package com.softwarehouse.serviceorder.security.controller;

import com.softwarehouse.serviceorder.security.model.UserAuthenticationRequest;
import com.softwarehouse.serviceorder.security.model.UserAuthenticationResponse;
import com.softwarehouse.serviceorder.security.service.AuthenticationService;
import com.softwarehouse.serviceorder.security.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserAuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public UserAuthenticationController(final AuthenticationService authenticationService,
                                        final UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

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
