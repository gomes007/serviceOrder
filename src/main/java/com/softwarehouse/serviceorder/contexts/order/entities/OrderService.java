package com.softwarehouse.serviceorder.contexts.order.entities;

import com.softwarehouse.serviceorder.contexts.service.entities.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_services")
public class OrderService {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String details;
    private int quantity;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal totalValue;
}

