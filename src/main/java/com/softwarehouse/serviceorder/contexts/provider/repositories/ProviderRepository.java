package com.softwarehouse.serviceorder.contexts.provider.repositories;

import com.softwarehouse.serviceorder.contexts.provider.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
