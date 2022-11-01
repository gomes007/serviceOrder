package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.PositionSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionSalaryRepository extends JpaRepository<PositionSalary, Long> {
    Page<PositionSalary> findAllByPositionIgnoreCaseContainingAndRoleIgnoreCaseContaining(String position, String role, Pageable pageable);
}
