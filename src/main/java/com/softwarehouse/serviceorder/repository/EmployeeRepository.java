package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Employee;
import com.softwarehouse.serviceorder.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
