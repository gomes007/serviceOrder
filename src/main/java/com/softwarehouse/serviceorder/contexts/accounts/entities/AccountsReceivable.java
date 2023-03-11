package com.softwarehouse.serviceorder.contexts.accounts.entities;

import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "accounts_receivable")
public class AccountsReceivable extends Billing {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accounts_receivable_attachments",
            joinColumns = @JoinColumn(name = "accounts_receivable_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> attachments;
}
