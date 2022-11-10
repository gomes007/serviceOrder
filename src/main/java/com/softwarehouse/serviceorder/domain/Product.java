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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String productName;
    private String barCode;
    private String weight;
    private String height;
    private String length;
    private String description;
    private String commission;

    @Embedded
    private Details details;

    @Embedded
    private Price price;

    @JoinColumn(name = "inventory_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;

    @OneToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Attachment> photos;
}
