package by.edik.car_api.web.controller;

import by.edik.car_api.web.controller.error.ApiErrorResponse;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        log.warn("SERVICE got exception: " + request.getAttribute(ERROR_EXCEPTION));

        Object errorMessage = request.getAttribute(ERROR_MESSAGE);
        Object errorStatusCode = request.getAttribute(ERROR_STATUS_CODE);
        Object errorExceptionType = request.getAttribute(ERROR_EXCEPTION_TYPE);

        executeWithResult(response, () -> ApiErrorResponse.builder()
            .errorMessage((errorMessage != null) ? errorMessage.toString() : null)
            .errorStatusCode((errorStatusCode != null) ? (Integer) errorStatusCode : null)
            .errorExceptionType((errorExceptionType != null) ? errorExceptionType.toString() : null)
            .build());
    }
}
