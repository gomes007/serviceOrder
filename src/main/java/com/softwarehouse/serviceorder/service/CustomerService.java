package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Customer;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Response<Customer> find(final PageRequest pageRequest) {
        Page<Customer> customers = this.repository.findAll(PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize()));
        return Response
                .<Customer>builder()
                .items(customers.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(customers.getTotalElements())
                .build();
    }
}
