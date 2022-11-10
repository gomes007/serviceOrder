package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.CostCenter;
import com.softwarehouse.serviceorder.domain.CostCenterStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {
    Page<CostCenter> findAllByNameIgnoreCaseContaining(
            String name,
            Pageable pageable
    );

    Page<CostCenter> findAllByNameIgnoreCaseContainingAndStatus(
            String name,
            CostCenterStatus status,
            Pageable pageable
    );
}
