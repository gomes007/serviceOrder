package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.AccountPlan;
import com.softwarehouse.serviceorder.repository.AccountsPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountsPlanService {
    private final AccountsPlanRepository repository;

    public AccountsPlanService(final AccountsPlanRepository repository) {
        this.repository = repository;
    }

    public AccountPlan register(final AccountPlan plan) {
        return this.repository.save(plan);
    }

    public List<AccountPlan> findAll() {
        return this.repository.findAll();
    }
}
