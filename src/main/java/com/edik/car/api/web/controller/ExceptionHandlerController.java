package com.edik.car.api.web.controller;

import com.edik.car.api.web.controller.error.ApiErrorResponse;
import com.edik.car.api.web.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
            .errorExceptionType(e.getClass().getSimpleName())
            .errorMessage(e.getMessage())
            .build();
        return ResponseEntity.ok(apiErrorResponse);
    }
}
