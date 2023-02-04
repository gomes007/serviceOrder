package com.softwarehouse.serviceorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "billing_plan")
public class BillingPlan {
    @Id
    @Column(name = "account_plan_id", nullable = false)
    private Long accountPlanId;

    @OneToOne
    @JoinColumn(name = "accounts_receivable_id", nullable = true)
    private AccountsReceivable accountsReceivable = null;

    @OneToOne
    @JoinColumn(name = "accounts_payable_id", nullable = true)
    private AccountsPayable accountsPayable = null;
}
