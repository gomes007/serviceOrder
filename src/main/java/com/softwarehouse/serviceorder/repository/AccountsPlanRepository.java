package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.AccountPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsPlanRepository extends JpaRepository<AccountPlan, Long> {
}
