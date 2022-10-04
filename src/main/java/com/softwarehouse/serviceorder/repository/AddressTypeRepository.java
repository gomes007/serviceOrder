package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.model.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {
}
