package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.PaymentMethod;
import com.softwarehouse.serviceorder.domain.PaymentMethodAvailability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Page<PaymentMethod> findAllByNameIgnoreCaseContainingAndAvailability(
            String name,
            PaymentMethodAvailability availability,
            Pageable pageable
    );
}
