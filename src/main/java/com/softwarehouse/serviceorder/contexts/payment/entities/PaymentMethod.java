package com.softwarehouse.serviceorder.contexts.payment.entities;

import com.softwarehouse.serviceorder.contexts.payment.models.PaymentMethodAvailability;
import com.softwarehouse.serviceorder.contexts.payment.models.PaymentModality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodAvailability availability;

    private int maxInstallmentQuantity;
    private int installmentIntervalDays;
    private int firstInstallmentDelayDays;
    private boolean availableOnSellingStation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentModality modality;

    @Column(name = "bank_tax_amount")
    private BigDecimal bankTaxAmount;

    @Column(name = "credit_provider_tax_percent")
    private BigDecimal creditProviderTaxPercent;

    @Column(name = "late_fee_percent")
    private BigDecimal lateFeePercent;

    @Column(name = "late_payment_tax_percent")
    private BigDecimal latePaymentTaxPercent;

    @Column(name = "can_create_bill")
    private boolean canCreateBill;
}
