package com.softwarehouse.serviceorder.contexts.payment.repositories;

import com.softwarehouse.serviceorder.contexts.payment.entities.PaymentMethod;
import com.softwarehouse.serviceorder.contexts.payment.models.PaymentMethodAvailability;
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
