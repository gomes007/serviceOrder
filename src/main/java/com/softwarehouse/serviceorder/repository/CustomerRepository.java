package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
