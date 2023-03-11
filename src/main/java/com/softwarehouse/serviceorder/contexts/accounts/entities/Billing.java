package com.softwarehouse.serviceorder.contexts.accounts.entities;

import com.softwarehouse.serviceorder.contexts.accounts.models.BillingOccurrence;
import com.softwarehouse.serviceorder.contexts.accounts.models.BillingEntity;
import com.softwarehouse.serviceorder.contexts.costcenter.entities.CostCenter;
import com.softwarehouse.serviceorder.contexts.customer.entities.Customer;
import com.softwarehouse.serviceorder.contexts.employee.entities.Employee;
import com.softwarehouse.serviceorder.contexts.payment.entities.Payment;
import com.softwarehouse.serviceorder.contexts.provider.entities.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingOccurrence occurrence;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private BigDecimal fee;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingEntity entity;

    @OneToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "cost_center_id", nullable = false)
    private CostCenter costCenter;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private String observations;
}
