package com.softwarehouse.serviceorder.contexts.product.services;

import com.softwarehouse.serviceorder.contexts.product.entities.Inventory;
import com.softwarehouse.serviceorder.exceptions.impl.NotFoundException;
import com.softwarehouse.serviceorder.contexts.product.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(final InventoryRepository repository) {
        this.repository = repository;
    }

    public void addAmount(final Long id, final int amount) {
        var found = this.findById(id);
        found.setCurrentQuantity(found.getCurrentQuantity() + amount);
        this.repository.save(found);
    }

    public void subtractAmount(final Long id, final int amount) {
        var found = this.findById(id);
        found.setCurrentQuantity(found.getCurrentQuantity() - amount);
        this.repository.save(found);
    }

    private Inventory findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("inventory not found"));
    }
}
