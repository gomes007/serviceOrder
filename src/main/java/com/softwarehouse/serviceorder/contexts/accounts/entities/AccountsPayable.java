package com.softwarehouse.serviceorder.contexts.accounts.entities;

import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts_payable")
public class AccountsPayable extends Billing {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accounts_payable_attachments",
            joinColumns = @JoinColumn(name = "accounts_payable_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> attachments;
}
