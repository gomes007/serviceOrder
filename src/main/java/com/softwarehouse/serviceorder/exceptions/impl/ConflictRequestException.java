package com.softwarehouse.serviceorder.exceptions.impl;

import com.softwarehouse.serviceorder.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class ConflictRequestException extends WebException {
    public ConflictRequestException(final String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
