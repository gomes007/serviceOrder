package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByNameIgnoreCaseContaining(String name, Pageable pageable);

    Page<Employee> findAllByNameIgnoreCaseContainingAndPositionSalaryPositionIgnoreCaseContainingAndPositionSalaryRoleIgnoreCaseContaining(String name, String position, String role, Pageable pageable);
}
