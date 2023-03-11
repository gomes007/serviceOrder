package com.softwarehouse.serviceorder.contexts.customer.entities;


import com.softwarehouse.serviceorder.contexts.shared.entities.Contact;
import com.softwarehouse.serviceorder.contexts.shared.entities.GeneralInformation;
import com.softwarehouse.serviceorder.contexts.address.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal creditLimit;

    private Boolean creditLimitExceeded;

    @Embedded
    private GeneralInformation generalInformation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_contacts",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts;

    @JoinColumn(name = "customer_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
}
