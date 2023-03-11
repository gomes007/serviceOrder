package com.softwarehouse.serviceorder.contexts.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {
    private String login;
    private String password;
}
