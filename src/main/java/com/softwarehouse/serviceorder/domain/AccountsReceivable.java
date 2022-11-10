package com.softwarehouse.serviceorder.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "accounts_receivable")
public class AccountsReceivable extends Billing {
    @OneToMany
    @JoinColumn(name = "accounts_receivable_id", nullable = false)
    private List<AccountPlan> accountPlans;

    @OneToMany
    @JoinColumn(name = "accounts_receivable_id", nullable = false)
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "accounts_receivable_id")
    private List<Attachment> attachments;
}
