package com.edik.car.api.web.controller;

import com.edik.car.api.service.UserService;
import com.edik.car.api.service.impl.UserServiceImpl;
import com.edik.car.api.web.dto.request.CreateUserRequest;
import com.edik.car.api.web.dto.request.UpdateUserRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/api/v1/users")
public class UsersController extends BaseController {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithStatusOk(resp, userService::getAll);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("POST method running.");
        log.info("Transferred params: {}", req.getQueryString());

        executeWithStatusCreated(resp, () ->
            userService.create(getRequestObject(req, CreateUserRequest.class)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("PUT method running.");
        log.info("Transferred params: {}", req.getQueryString());

        executeWithStatusCreated(resp, () ->
            userService.update(getRequestObject(req, UpdateUserRequest.class)));
    }
}
