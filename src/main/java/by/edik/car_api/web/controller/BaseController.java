package by.edik.car_api.web.controller;

import by.edik.car_api.web.exception.ServerFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Supplier;

import static by.edik.car_api.config.ServletConstants.APPLICATION_JSON;
import static by.edik.car_api.config.ServletConstants.UTF_8;

public abstract class BaseController extends HttpServlet {

    private final Logger log = Logger.getLogger(BaseController.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    protected static void setResponseConfiguration(HttpServletResponse resp) {
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(request.getMethod())) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
    }

    protected void executeWithResult(HttpServletResponse resp,
                                     Supplier<Object> getResult) {
        setResponseConfiguration(resp);

        try (PrintWriter writer = resp.getWriter()) {
            log.info("writer created");
            Object o = getResult.get();
            log.info("after lambda " + o);
            String json = writeAsString(o);
            writer.write(json);
            writer.flush();
            log.info("Data returned to the client: " + json);
        } catch (IOException e) {
            log.warn("Server failed.", e);
            throw new ServerFailedException("Server failed.", e);
        }
    }

    protected String writeAsString(Object o) {
        try {
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new ServerFailedException("Mapping process failed.", e);
        }
    }
}
