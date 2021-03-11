package by.edik.car_api.web.controller;

import by.edik.car_api.dao.exception.ObjectNotFoundException;
import by.edik.car_api.web.controller.error.ApiErrorResponse;
import by.edik.car_api.web.exception.BadRequestException;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.edik.car_api.config.HttpStatusCodes.BAD_REQUEST;
import static by.edik.car_api.config.HttpStatusCodes.INTERNAL_ERROR;
import static by.edik.car_api.config.HttpStatusCodes.NOT_FOUND;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@WebServlet("/errorHandler")
public class ErrorHandlerController extends BaseController {

    private final Logger log = Logger.getLogger(ErrorHandlerController.class);

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
