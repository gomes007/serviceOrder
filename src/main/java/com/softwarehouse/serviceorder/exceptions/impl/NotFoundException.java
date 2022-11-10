package com.softwarehouse.serviceorder.exceptions.impl;

import com.softwarehouse.serviceorder.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends WebException {
    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
