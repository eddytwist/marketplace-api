package com.edik.car.api.web.controller;

import com.edik.car.api.web.controller.error.ApiErrorResponse;
import com.edik.car.api.web.exception.BadRequestException;
import com.edik.car.api.web.exception.BadRequestParamsException;
import com.edik.car.api.web.exception.NotValidIdException;
import com.edik.car.api.web.exception.ServerFailedException;
import com.edik.car.api.web.exception.UnsupportedMediaTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest()
            .body(
                ApiErrorResponse.builder()
                    .errorExceptionType(e.getClass().getSimpleName())
                    .errorMessage(e.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(BadRequestParamsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestParamsException(BadRequestParamsException e) {
        return ResponseEntity.badRequest()
            .body(
                ApiErrorResponse.builder()
                    .errorExceptionType(e.getClass().getSimpleName())
                    .errorMessage(e.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(ServerFailedException.class)
    public ResponseEntity<ApiErrorResponse> handleServerFailedException(ServerFailedException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiErrorResponse.builder()
                    .errorExceptionType(e.getClass().getSimpleName())
                    .errorMessage(e.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(NotValidIdException.class)
    public ResponseEntity<ApiErrorResponse> handleNotValidIdException(NotValidIdException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiErrorResponse.builder()
                    .errorExceptionType(e.getClass().getSimpleName())
                    .errorMessage(e.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleUnsupportedMediaTypeException(UnsupportedMediaTypeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiErrorResponse.builder()
                    .errorExceptionType(e.getClass().getSimpleName())
                    .errorMessage(e.getMessage())
                    .build()
            );
    }
}

//TODO<>
//1. Доделать контроллеры
//2. Переехать на Spring data
//3. Написать свой Аспект по выводу в консоль hello world для всех вызовов контроллера
//4. Написать тест(ы) на контроллер
//5. EntityGrapth
//6. Spring Security. @Secured
