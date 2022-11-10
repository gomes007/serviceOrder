package com.softwarehouse.serviceorder.utils;

import com.softwarehouse.serviceorder.domain.Product;
import com.softwarehouse.serviceorder.model.InventoryValidation;

import java.util.Optional;

public class InventoryValidationUtils {
    private InventoryValidationUtils() {
    }

    public static InventoryValidation validate(final int requestQuantity, final Product product) {
        return Optional
                .ofNullable(product)
//                .map(p -> p.getInventory()) // lambda com apenas um argumento que tem um metodo chamado
                .map(Product::getInventory) // referencia ao método (o map entende da mesma forma que a lambda)
                .map(inventory -> {
                    final boolean unavailable = inventory.getCurrentQuantity() < requestQuantity;
                    final String errorMessage = unavailable ? "product below availability" : "";

                    return InventoryValidation
                            .builder()
                            .availableQuantity(inventory.getCurrentQuantity())
                            .requestedQuantity(requestQuantity)
                            .errorMessage(errorMessage)
                            .unavailable(unavailable)
                            .build();
                })
                .orElse(InventoryValidation
                        .builder()
                        .availableQuantity(0)
                        .requestedQuantity(requestQuantity)
                        .errorMessage("failed to get product inventory")
                        .unavailable(true)
                        .build());
    }
}
