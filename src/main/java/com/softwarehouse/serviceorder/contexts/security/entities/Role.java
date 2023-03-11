package com.softwarehouse.serviceorder.contexts.security.entities;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
