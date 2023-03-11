package com.softwarehouse.serviceorder.contexts.accounts.controllers;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPlan;
import com.softwarehouse.serviceorder.contexts.accounts.services.AccountsPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Accounts Plans")
@RequestMapping("/accounts-plan")
public class AccountsPlanController {
    private final AccountsPlanService service;

    public AccountsPlanController(final AccountsPlanService service) {
        this.service = service;
    }

    @PostMapping
    public AccountsPlan register(@RequestBody AccountsPlan accountsPlan) {
        return this.service.register(accountsPlan);
    }

    @GetMapping
    public List<AccountsPlan> findAll() {
        return this.service.findAll();
    }
}
