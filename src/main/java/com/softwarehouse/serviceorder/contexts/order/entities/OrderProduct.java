package com.softwarehouse.serviceorder.contexts.order.entities;

import com.softwarehouse.serviceorder.contexts.product.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String details;
    private int quantity;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal totalValue;
}
