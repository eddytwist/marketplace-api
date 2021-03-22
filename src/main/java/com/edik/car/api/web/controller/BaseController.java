package com.edik.car.api.web.controller;

import com.edik.car.api.config.HttpStatusCodes;
import com.edik.car.api.config.ServletConstants;
import com.edik.car.api.web.exception.BadRequestParamsException;
import com.edik.car.api.web.exception.ServerFailedException;
import com.edik.car.api.web.exception.UnsupportedMediaTypeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseController extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throwIfNotJsonForSpecificMethod(request);

        if ("PATCH".equalsIgnoreCase(request.getMethod())) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) {
    }

    protected static void setResponseConfiguration(HttpServletResponse resp) {
        resp.setContentType(ServletConstants.APPLICATION_JSON);
        resp.setCharacterEncoding(ServletConstants.UTF_8);
    }

    protected String writeAsString(Object o) {
        try {
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn("Mapping process failed.", e);
            throw new ServerFailedException("Mapping process failed.", e);
        }
    }

    private static void throwIfNotJsonForSpecificMethod(HttpServletRequest request) {
        boolean applicationJson = ServletConstants.APPLICATION_JSON.equals(request.getHeader("Content-Type"));
        boolean methodThatShouldBeApplicationJson = "POST".equalsIgnoreCase(request.getMethod())
            || "PATCH".equalsIgnoreCase(request.getMethod())
            || "PUT".equalsIgnoreCase(request.getMethod());

        if (!applicationJson && methodThatShouldBeApplicationJson) {
            log.warn("Unsupported content type: {}", request.getHeader("Content-Type"));
            throw new UnsupportedMediaTypeException("Unsupported content type.");
        }
    }

    protected void executeWithStatusOk(HttpServletResponse resp,
                                       Supplier<Object> getResult) {
        execute(resp, getResult);
    }

    protected void executeWithStatusCreated(HttpServletResponse resp,
                                            Supplier<Object> getResult) {
        execute(resp, getResult);
        resp.setStatus(HttpStatusCodes.CREATED);
    }

    protected void execute(HttpServletResponse resp, Supplier<Object> getResult) {
        log.info("Executing.");
        setResponseConfiguration(resp);

        try {
            Object o = getResult.get();
            log.info("Object for mapping: {}", o);

            String json = writeAsString(o);

            PrintWriter writer = resp.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
            log.info("Data returned to the client: {}", json);
        } catch (IOException e) {
            log.warn("Server failed.", e);
            throw new ServerFailedException("Server failed.", e);
        }
    }

    protected <T> T getRequestObject(HttpServletRequest req, Class<T> clazz) {
        String json = getJson(req);
        try {
            log.info("JSON for mapping: {}", json);
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.warn("Wrong input parameters.", e);
            throw new BadRequestParamsException("Wrong input parameters.", e);
        }
    }

    protected String getJson(HttpServletRequest request) {
        try {
            String json = request.getReader().lines().collect(Collectors.joining());
            log.debug("Body parsed from request: {}", json);
            return json;
        } catch (IOException e) {
            log.warn("Impossible to read.", e);
            throw new BadRequestParamsException("Impossible to read.", e);
        }
    }
}
