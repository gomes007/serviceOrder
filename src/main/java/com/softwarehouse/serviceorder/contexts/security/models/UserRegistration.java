package com.softwarehouse.serviceorder.contexts.security.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistration {
    private String url;
    private String email;
    private String password;
}
