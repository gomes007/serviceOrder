package com.softwarehouse.serviceorder.contexts.employee.repositories;

import com.softwarehouse.serviceorder.contexts.employee.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByNameIgnoreCaseContaining(String name, Pageable pageable);

    Page<Employee> findAllByNameIgnoreCaseContainingAndPositionSalaryPositionIgnoreCaseContainingAndPositionSalaryRoleIgnoreCaseContaining(String name, String position, String role, Pageable pageable);

    Optional<Employee> findByEmail(final String email);
}
