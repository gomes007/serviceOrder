package com.softwarehouse.serviceorder.repository;

import com.softwarehouse.serviceorder.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
