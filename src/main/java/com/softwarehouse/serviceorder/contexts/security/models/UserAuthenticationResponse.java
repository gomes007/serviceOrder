package com.softwarehouse.serviceorder.contexts.security.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserAuthenticationResponse {
    private String login;
    private List<SimpleGrantedAuthority> roles;
    private LocalDateTime expirationTime;
}
