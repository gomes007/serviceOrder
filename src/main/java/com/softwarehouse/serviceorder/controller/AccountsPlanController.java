package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.AccountPlan;
import com.softwarehouse.serviceorder.service.AccountsPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts-plan")
public class AccountsPlanController {
    private final AccountsPlanService service;

    public AccountsPlanController(final AccountsPlanService service) {
        this.service = service;
    }

    @PostMapping
    public AccountPlan register(@RequestBody AccountPlan accountPlan) {
        return this.service.register(accountPlan);
    }

    @GetMapping
    public List<AccountPlan> findAll() {
        return this.service.findAll();
    }
}
