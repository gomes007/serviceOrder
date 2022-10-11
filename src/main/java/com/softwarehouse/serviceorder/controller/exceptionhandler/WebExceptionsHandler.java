package com.softwarehouse.serviceorder.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class WebExceptionsHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(final SQLIntegrityConstraintViolationException ex) {
        final Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.put("message", ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")) {
            status = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(status).body(response);
    }
}
