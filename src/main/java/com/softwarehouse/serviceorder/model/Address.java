package com.softwarehouse.serviceorder.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String street;
    private String number;
    private String neighborhood;
    private String zipCode;
    private String complement;
    private String city;
    private String state;


    @OneToOne
    @JoinTable
            (name = "address_addressType",
                    joinColumns = @JoinColumn(name = "address_id"),
                    inverseJoinColumns = @JoinColumn(name = "address_type_id"))
    private AddressType addressType;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;


    @JsonIgnore
    @OneToOne(optional = false)
    @JoinColumn(name = "service_order_id")
    private ServiceOrder service_order;

}
