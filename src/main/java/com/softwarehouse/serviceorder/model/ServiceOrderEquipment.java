package com.softwarehouse.serviceorder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_order_equipment")
public class ServiceOrderEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinTable(
            name = "service_order_equipments",
            joinColumns = @JoinColumn(name = "service_order_equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Product product;

    private String details;
    private int quantity;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal totalValue;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "service_order_id")
    private ServiceOrder service_order;

}
