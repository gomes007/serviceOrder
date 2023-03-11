package com.softwarehouse.serviceorder.contexts.payment.entities;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "accounts_plan_id", nullable = false)
    private AccountsPlan accountsPlan;

    @Column(nullable = false)
    private BigDecimal value;

    private BigDecimal discount;

    @Column(name = "other_information")
    private String otherInformation;

    private boolean processed;
}
