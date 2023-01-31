package com.softwarehouse.serviceorder.security.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "users")
@Entity
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true, name = "external_reference")
    private Long externalReference;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<UserRoles> roles;

    @Column(name = "first_access", nullable = false)
    private boolean firstAccess = true;

    public List<String> getStringRoles() {
        return this.roles.stream().map(userRole -> userRole.getRole().getName()).toList();
    }
}
