package com.softwarehouse.serviceorder.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "accounts_payable")
public class AccountsPayable extends Billing {
    @OneToMany
    @JoinColumn(name = "accounts_payable_id", nullable = false)
    private List<AccountPlan> accountPlans;

    @OneToMany
    @JoinColumn(name = "accounts_payable_id", nullable = false)
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "accounts_payable_id")
    private List<Attachment> attachments;
}
