package by.edik.car_api.web.controller;

import by.edik.car_api.web.controller.error.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@WebServlet("/errorHandler")
public class ErrorHandlerController extends BaseController {

    private final Logger log = Logger.getLogger(ErrorHandlerController.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.info("ERROR SERVICE method running.");
        log.warn("SERVICE got exception: " + request.getAttribute(ERROR_EXCEPTION));

        setResponseConfiguration(response);

        ObjectMapper mapper = new ObjectMapper();
        try (PrintWriter writer = response.getWriter()) {
            Arrays.asList(ERROR_STATUS_CODE, ERROR_EXCEPTION_TYPE)
                .forEach(e ->
                    writer.write(e + ": " + request.getAttribute(e) + "\n")
                );
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
            apiErrorResponse.setErrorMessage(request.getAttribute(ERROR_MESSAGE).toString());
            String json = mapper.writeValueAsString(apiErrorResponse);
            writer.write(json);
            log.info("Data returned to the client:\n" + json);

//        executeWithResult(response, () -> {
//            log.info("start lambda");
//            ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
//                .errorMessage(request.getAttribute(ERROR_MESSAGE).toString())
//                .errorStatusCode((Integer) (request.getAttribute(ERROR_STATUS_CODE)))
//                .errorExceptionType(request.getAttribute(ERROR_EXCEPTION_TYPE).toString())
//                .build();
//            log.info("end lambda");
//            return apiErrorResponse;
//        });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
