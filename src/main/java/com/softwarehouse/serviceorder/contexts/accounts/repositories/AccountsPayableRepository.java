package com.softwarehouse.serviceorder.contexts.accounts.repositories;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPayable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, Long> {
}
