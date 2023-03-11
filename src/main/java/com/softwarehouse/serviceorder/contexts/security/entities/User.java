package com.softwarehouse.serviceorder.contexts.security.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true, name = "external_reference")
    private Long externalReference;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(name = "first_access", nullable = false)
    private boolean firstAccess = true;

    public List<String> getStringRoles() {
        return this.roles.stream().map(Role::getName).toList();
    }
}
