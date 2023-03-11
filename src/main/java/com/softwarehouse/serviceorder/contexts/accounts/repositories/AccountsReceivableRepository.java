package com.softwarehouse.serviceorder.contexts.accounts.repositories;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsReceivable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsReceivableRepository extends JpaRepository<AccountsReceivable, Long> {
}
