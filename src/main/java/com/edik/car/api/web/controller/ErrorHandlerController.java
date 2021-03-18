package com.edik.car.api.web.controller;

import com.edik.car.api.dao.exception.ObjectNotFoundException;
import com.edik.car.api.web.controller.error.ApiErrorResponse;
import com.edik.car.api.web.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.edik.car.api.config.HttpStatusCodes.BAD_REQUEST;
import static com.edik.car.api.config.HttpStatusCodes.INTERNAL_ERROR;
import static com.edik.car.api.config.HttpStatusCodes.NOT_FOUND;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Slf4j
@WebServlet("/errorHandler")
public class ErrorHandlerController extends BaseController {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        log.info("ERROR SERVICE method running.");
        Throwable e = (Throwable) request.getAttribute(ERROR_EXCEPTION);
        log.warn("SERVICE got exception", e);

        Object errorMessage = request.getAttribute(ERROR_MESSAGE);
        Object errorStatusCode = request.getAttribute(ERROR_STATUS_CODE);
        Object errorExceptionType = request.getAttribute(ERROR_EXCEPTION_TYPE);

        response.setStatus(getErrorStatusCode(e, errorStatusCode));

        execute(response, () -> ApiErrorResponse.builder()
            .errorMessage(errorMessage != null ? errorMessage.toString() : null)
            .errorExceptionType(errorExceptionType != null ? errorExceptionType.toString() : null)
            .build());
    }

    private static Integer getErrorStatusCode(Throwable e, Object errorStatusCode) {
        if (e instanceof ObjectNotFoundException) {
            return NOT_FOUND;
        } else if (e instanceof BadRequestException) {
            return BAD_REQUEST;
        }

        return errorStatusCode != null
            ? (Integer) errorStatusCode
            : INTERNAL_ERROR;
    }
}
