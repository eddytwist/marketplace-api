package com.edik.car.api.web.controller;

import com.edik.car.api.service.UserService;
import com.edik.car.api.service.impl.UserServiceImpl;
import com.edik.car.api.web.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/api/v1/users/*")
public class UserByIdController extends BaseController {

    private final UserService userService = UserServiceImpl.getInstance();
    private final UserServiceImpl userServiceHiba = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("GET method running.");

        executeWithStatusOk(resp, () -> {
            Long userId = UriUtils.getId(req.getPathInfo());

            return userServiceHiba.getById(userId);
        });
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("DELETE method running.");

        Long userId = UriUtils.getId(req.getPathInfo());

        userServiceHiba.delete(userId);
        log.info("User id = " + userId + " successfully deleted.");
    }
}
