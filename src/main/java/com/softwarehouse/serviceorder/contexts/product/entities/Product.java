package com.softwarehouse.serviceorder.contexts.product.entities;


import com.softwarehouse.serviceorder.contexts.provider.entities.Provider;
import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;
import com.softwarehouse.serviceorder.contexts.shared.entities.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_photos",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> photos;
}
