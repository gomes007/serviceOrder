package com.softwarehouse.serviceorder.contexts.customer.repositories;

import com.softwarehouse.serviceorder.contexts.customer.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findAllByGeneralInformationNameIgnoreCaseContainingOrGeneralInformationEmailIgnoreCaseContainingOrGeneralInformationCpfContainingOrGeneralInformationCnpjContaining(
            String name,
            String email,
            String cpf,
            String cnpj,
            Pageable pageable
    );
}
