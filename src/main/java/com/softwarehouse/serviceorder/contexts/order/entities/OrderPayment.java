package com.softwarehouse.serviceorder.contexts.order.entities;

import com.softwarehouse.serviceorder.contexts.payment.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_payments")
public class OrderPayment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "payment_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
}
