package com.softwarehouse.serviceorder.service;

import com.softwarehouse.serviceorder.domain.Employee;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.model.Response;
import com.softwarehouse.serviceorder.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(final EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee save(final Employee employee) {
        return this.repository.save(employee);
    }

    public Response<Employee> find(final PageRequest pageRequest) {
        Page<Employee> employees = this.repository.findAll(PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize()));
        return Response
                .<Employee>builder()
                .items(employees.getContent())
                .currentPage(Long.parseLong("" + pageRequest.getPageNumber()))
                .itemsPerPage(Long.parseLong("" + pageRequest.getPageSize()))
                .totalRecordsQuantity(employees.getTotalElements())
                .build();
    }

    public Employee findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("employee not found"));
    }

    public void employeeExists(final Long id) {
        this.findById(id);
    }

    public Employee deleteById(final Long id) {
        final Employee found = this.findById(id);

        this.repository.delete(found);

        return found;
    }
}
