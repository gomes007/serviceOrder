package com.softwarehouse.serviceorder.controller;

import com.softwarehouse.serviceorder.domain.Customer;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.service.CustomerService;
import org.springframework.data.domain.PageRequest;
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
    public Customer register(@RequestBody final Customer customer) {
        return this.service.save(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Customer> find(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        return this.service.find(PageRequest.of(page, size));
    }
}
