package com.softwarehouse.serviceorder.contexts.service.repositories;

import com.softwarehouse.serviceorder.contexts.service.entities.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Page<Service> findAllByServiceNameIgnoreCaseContaining(String name, Pageable pageable);
}
