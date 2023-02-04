package com.softwarehouse.serviceorder.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "accounts_receivable")
public class AccountsReceivable extends Billing {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountsReceivable")
    private List<BillingPlan> accountPlans;

    @OneToMany
    @JoinColumn(name = "accounts_receivable_id")
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "accounts_receivable_id")
    private List<Attachment> attachments;
}
