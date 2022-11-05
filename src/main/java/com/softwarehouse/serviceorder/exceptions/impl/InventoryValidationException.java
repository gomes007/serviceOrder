package com.softwarehouse.serviceorder.exceptions.impl;

import com.softwarehouse.serviceorder.exceptions.WebException;
import com.softwarehouse.serviceorder.model.InventoryValidation;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class InventoryValidationException extends WebException {
    public InventoryValidationException(final String message, final InventoryValidation validation) {
        super(message, HttpStatus.BAD_REQUEST, Map.of(
                "requestedQuantity", validation.getRequestedQuantity(),
                "availbleQuantity", validation.getAvailableQuantity()
        ));
    }
}
