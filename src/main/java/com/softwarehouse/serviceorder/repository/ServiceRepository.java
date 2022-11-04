package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Page<Service> findAllByServiceNameIgnoreCaseContaining(String name, Pageable pageable);
}
