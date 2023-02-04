package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.AccountsReceivable;
import com.softwarehouse.serviceorder.repository.AccountsReceivableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountsReceivableService {
    private final AccountsReceivableRepository repository;

    public AccountsReceivableService(final AccountsReceivableRepository repository) {
        this.repository = repository;
    }

    @Async
    public void registerAccount(final AccountsReceivable accountsReceivable) {
        log.info("registering account to receive");
        this.repository.save(accountsReceivable);
    }
}
