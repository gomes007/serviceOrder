package com.softwarehouse.serviceorder.contexts.accounts.services;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPlan;
import com.softwarehouse.serviceorder.contexts.accounts.repositories.AccountsPlanRepository;
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

    public AccountsPlan register(final AccountsPlan plan) {
        return this.repository.save(plan);
    }

    public List<AccountsPlan> findAll() {
        return this.repository.findAll();
    }
}
