package com.softwarehouse.serviceorder.contexts.accounts.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts_plans")
public class AccountsPlan {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @Column(nullable = false)
    private boolean isReceivingAccount;

    @Column(nullable = false)
    private boolean isPayingAccount;
}
