package com.softwarehouse.serviceorder.exceptions.impl;

import com.softwarehouse.serviceorder.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class InternalServerException extends WebException {
    public InternalServerException(final String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
