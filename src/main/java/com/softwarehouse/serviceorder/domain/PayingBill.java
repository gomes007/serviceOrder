package com.softwarehouse.serviceorder.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "paying_bills")
public class PayingBill extends Billing {
    @OneToMany
    @JoinColumn(name = "paying_bill_id", nullable = false)
    private List<AccountPlan> accountPlans;

    @OneToMany
    @JoinColumn(name = "paying_bill_id", nullable = false)
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "paying_bill_id")
    private List<Attachment> attachments;
}
