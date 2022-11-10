package com.softwarehouse.serviceorder.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private GeneralInformation generalInformation;

    @JoinColumn(name = "provider_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
}
