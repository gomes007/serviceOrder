package com.softwarehouse.serviceorder.security.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_roles")
public class UserRoles {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
    private Role role;
}
