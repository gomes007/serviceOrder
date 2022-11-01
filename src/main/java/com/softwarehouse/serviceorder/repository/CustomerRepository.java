package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Customer;
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
