package by.edik.car_api.web.controller;

import by.edik.car_api.web.controller.error.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static by.edik.car_api.config.ServletConstants.APPLICATION_JSON;
import static by.edik.car_api.config.ServletConstants.UTF_8;
import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION_TYPE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@WebServlet(urlPatterns = "/errorHandler")
public class ErrorHandlerController extends HttpServlet {

    private final Logger log = Logger.getLogger(ErrorHandlerController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET method running.");
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        try (PrintWriter writer = resp.getWriter()) {
            Arrays.asList(ERROR_STATUS_CODE, ERROR_EXCEPTION_TYPE)
                .forEach(e ->
                    writer.write(e + ": " + req.getAttribute(e) + '\n')
                );
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
            apiErrorResponse.setErrorMessage((String) req.getAttribute(ERROR_MESSAGE));
            String json = mapper.writeValueAsString(apiErrorResponse);
            writer.write(json);
            log.info("Data returned to the client: " + json);
        }
    }
}
