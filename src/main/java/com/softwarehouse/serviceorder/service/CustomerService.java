package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Customer;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
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

    public Response<Customer> find(final PageRequest pageRequest, final String filter) {
        Page<Customer> customers = this.repository.findAllByGeneralInformationNameIgnoreCaseContainingOrGeneralInformationEmailIgnoreCaseContainingOrGeneralInformationCpfContainingOrGeneralInformationCnpjContaining(
                filter,
                filter,
                filter,
                filter,
                PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
        );
        return Response
                .<Customer>builder()
                .items(customers.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(customers.getTotalElements())
                .build();
    }

    public Customer findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("customer not found"));
    }

    public void customerExists(final Long id) {
        this.findById(id);
    }

    public Customer deleteById(final Long id) {
        final Customer found = this.findById(id);

        this.repository.delete(found);

        return found;
    }
}
