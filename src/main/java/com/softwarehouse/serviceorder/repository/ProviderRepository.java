package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Customer;
import com.softwarehouse.serviceorder.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
