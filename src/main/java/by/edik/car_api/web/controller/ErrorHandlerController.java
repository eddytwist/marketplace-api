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

import static by.edik.car_api.config.ServletConstants.CHARACTER_ENCODING;
import static by.edik.car_api.config.ServletConstants.CONTENT_TYPE;
import static javax.servlet.RequestDispatcher.*;

@WebServlet(urlPatterns = "/errorHandler")
public class ErrorHandlerController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ErrorHandlerController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("GET method running.");
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(CHARACTER_ENCODING);
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try (PrintWriter writer = resp.getWriter()) {
            Arrays.asList(ERROR_STATUS_CODE, ERROR_EXCEPTION_TYPE)
                    .forEach(e ->
                            writer.write(e + ": " + req.getAttribute(e) + "\n")
                    );
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
            apiErrorResponse.setErrorMessage((String) req.getAttribute(ERROR_MESSAGE));
            json = mapper.writeValueAsString(apiErrorResponse);
            writer.write(json);
            LOG.info("Data returned to the client:\n" + json);
        }
    }
}
