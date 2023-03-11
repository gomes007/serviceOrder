package com.softwarehouse.serviceorder.contexts.provider.entities;


import com.softwarehouse.serviceorder.contexts.address.entities.Address;
import com.softwarehouse.serviceorder.contexts.shared.entities.Contact;
import com.softwarehouse.serviceorder.contexts.shared.entities.GeneralInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "provider")
public class Provider {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private GeneralInformation generalInformation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "provider_contacts",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts;

    @JoinColumn(name = "provider_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
}
