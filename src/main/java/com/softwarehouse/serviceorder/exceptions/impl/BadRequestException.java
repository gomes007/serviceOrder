package com.softwarehouse.serviceorder.exceptions.impl;

import com.softwarehouse.serviceorder.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends WebException {
    public BadRequestException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
