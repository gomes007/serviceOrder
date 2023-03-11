package com.softwarehouse.serviceorder.contexts.product.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryValidation {
    private int requestedQuantity;
    private int availableQuantity;
    private boolean unavailable;
    private String errorMessage;
}
