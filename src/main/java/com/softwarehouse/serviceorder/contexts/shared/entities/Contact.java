package com.softwarehouse.serviceorder.contexts.shared.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
public class Contact {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "contact_type_id", nullable = false)
    private ContactType contactType;

    @Column(nullable = false)
    private String contact;

    private String occupation;

    private String observations;
}
