package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.model.Customer;
import com.softwarehouse.serviceorder.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(final CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer save(final Customer customer) {
        return this.repository.save(customer);
    }
}
