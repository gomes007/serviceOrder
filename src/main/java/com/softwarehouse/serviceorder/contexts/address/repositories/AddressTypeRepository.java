package com.softwarehouse.serviceorder.contexts.address.repositories;

import com.softwarehouse.serviceorder.contexts.address.entities.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {
}
