package com.softwarehouse.serviceorder.contexts.accounts.repositories;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsPlanRepository extends JpaRepository<AccountsPlan, Long> {
}
