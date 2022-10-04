package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.model.Customer;
import com.softwarehouse.serviceorder.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(final CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody final Customer customer) {
        return this.service.save(customer);
    }
}
