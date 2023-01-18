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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    private List<Role> roles;

    public List<String> getStringRoles() {
        return this.roles.stream().map(Role::getName).toList();
    }
}
