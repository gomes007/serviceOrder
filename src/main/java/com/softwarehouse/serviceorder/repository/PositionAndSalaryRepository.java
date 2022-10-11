package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.AddressType;
import com.softwarehouse.serviceorder.domain.PositionAndSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionAndSalaryRepository extends JpaRepository<PositionAndSalary, Long> {
}
