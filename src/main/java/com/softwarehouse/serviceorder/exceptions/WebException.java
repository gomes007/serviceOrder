package com.softwarehouse.serviceorder.exceptions;

import org.springframework.http.HttpStatus;

public abstract class WebException extends RuntimeException {
    private final HttpStatus status;

    public WebException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
