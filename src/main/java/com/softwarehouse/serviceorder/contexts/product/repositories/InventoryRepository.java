package com.softwarehouse.serviceorder.contexts.product.repositories;

import com.softwarehouse.serviceorder.contexts.product.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
