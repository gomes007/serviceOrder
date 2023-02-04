package com.softwarehouse.serviceorder.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts_payable")
public class AccountsPayable extends Billing {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountsPayable")
    private List<BillingPlan> accountPlans;

    @OneToMany
    @JoinColumn(name = "accounts_payable_id")
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "accounts_payable_id")
    private List<Attachment> attachments;
}
