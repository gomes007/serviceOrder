package com.softwarehouse.serviceorder.contexts.employee.services;

import com.softwarehouse.serviceorder.contexts.employee.entities.Employee;
import com.softwarehouse.serviceorder.exceptions.impl.ConflictRequestException;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.contexts.shared.models.Response;
import com.softwarehouse.serviceorder.contexts.employee.repositories.EmployeeRepository;
import com.softwarehouse.serviceorder.contexts.security.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final UserService userService;

    public EmployeeService(final EmployeeRepository repository, final UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Employee save(final Employee employee) {
        this.throwConflictIfEmployeeAlreadyExists(employee);

        final Employee created = this.repository.save(employee);

        this.userService.createSystemUser(created.getEmail(), created.getId());

        return created;
    }

    public Response<Employee> find(final String filter, final String position, final String role, final PageRequest pageRequest) {
//        TODO rever regra de filtro por position e role
//        Page<Employee> employees = this.repository.findAllByNameIgnoreCaseContainingAndPositionSalaryPositionIgnoreCaseContainingAndPositionSalaryRoleIgnoreCaseContaining(
//                filter,
//                position,
//                role,
//                PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
//        );
        Page<Employee> employees = this.repository.findAllByNameIgnoreCaseContaining(
                filter,
                PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize())
        );
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

    public void throwConflictIfEmployeeAlreadyExists(final Employee employee) {
        this.repository.findByEmail(employee.getEmail()).ifPresent(found -> {
            throw new ConflictRequestException("employee already exists with email: %s".formatted(found.getEmail()));
        });
    }
}
