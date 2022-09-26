package com.softwarehouse.serviceorder.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal creditLimit;

    private Boolean creditLimitExceeded;

    @Embedded
    private GeneralInformation generalInformation;

    @JoinColumn(name = "client_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
}
